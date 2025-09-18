from abc import ABC, abstractmethod
from typing import override
from uuid import UUID
import logging
import time
import numpy as np

from bosdyn.client.frame_helpers import VISION_FRAME_NAME
from bosdyn.client.frame_helpers import get_vision_tform_body
from bosdyn.api.spot import robot_command_pb2 as spot_command_pb2
import bosdyn.client.lease
from bosdyn.client.robot_command import (
    RobotCommandBuilder,
    RobotCommandClient,
    blocking_stand,
)
from bosdyn.client.robot_state import RobotStateClient
from bosdyn.client.math_helpers import Quat
from bosdyn.client.world_object import WorldObjectClient
from bosdyn.api import world_object_pb2
from bosdyn.client.lease import LeaseClient
from bosdyn.client.frame_helpers import get_a_tform_b


from app.repositories.spot_robot_repository import RobotRepository

logger = logging.getLogger(__name__)


class RobotService(ABC):
    """
    Abstract base class for robot service.
    """

    @abstractmethod
    def move_to_target(self, robot_id: UUID, fiducial: int) -> bool:
        """
        Moves the robot to a target position.
        :return: True if the action was successful, False otherwise.
        """
        raise NotImplementedError("This method should be implemented by subclasses.")


class SpotRobotServiceImpl(RobotService):
    def __init__(self, robot_repository: RobotRepository):
        """
        Initializes the SpotRobotServiceImpl with a SpotRobot instance.
        :param robot_repository: The SpotRobot instance to control.
        """
        self.robot_repository = robot_repository

    @override
    def move_to_target(self, robot_id: UUID, fiducial: int) -> bool:
        """
        Moves the Spot robot with a given ID to the target fiducial.
        :param robot_id: UUID of the robot
        :param fiducial: ID of the fiducial tag to follow
        :return: True if successful, False otherwise
        """
        try:
            # Get the robot from the repository
            spot_robot = self.robot_repository.get_robot_by_id(robot_id)
            robot = self.robot_repository.get_spot_robot_delegate_by_id(robot_id)
            robot_credentials = self.robot_repository.get_spot_robot_by_id(robot_id)
            if spot_robot is None or robot is None or robot_credentials is None:
                logger.error(f"Robot with ID {robot_id} not found")
                return False

            bosdyn.client.util.setup_logging()
            sdk = bosdyn.client.create_standard_sdk("SpotFiducialFollower")
            robot = sdk.create_robot(
                robot_credentials.address
            )  # Replace with Spot’s IP
            robot.authenticate(robot_credentials.username, robot_credentials.password)

            robot.time_sync.wait_for_sync()

            lease_client = robot.ensure_client(LeaseClient.default_service_name)
            robot_state_client = robot.ensure_client(
                RobotStateClient.default_service_name
            )
            robot_command_client = robot.ensure_client(
                RobotCommandClient.default_service_name
            )

            with bosdyn.client.lease.LeaseKeepAlive(
                lease_client, must_acquire=True, return_at_exit=True
            ):
                robot.power_on()
                blocking_stand(robot_command_client)

                # Delay grabbing image until spot is standing (or close enough to upright).
                time.sleep(1)

                fiducial_rt_world = None

                fiducials = self.get_fiducial_objects(robot)

                # logging.warning(fiducials)

                for fid in fiducials:
                    if fid.apriltag_properties.tag_id == fiducial:
                        target_tform = get_a_tform_b(
                            fid.transforms_snapshot,
                            VISION_FRAME_NAME,
                            fid.apriltag_properties.frame_name_fiducial,
                        ).to_proto()
                        if target_tform is not None:
                            fiducial_rt_world = target_tform.position
                        break

                tag_position, _angle_desired = self.offset_tag_pose(
                    fiducial_rt_world, robot_state_client, 0.8
                )

                # Command the robot to go to the tag in kinematic odometry frame
                tag_cmd = RobotCommandBuilder.synchro_se2_trajectory_point_command(
                    goal_x=tag_position[0],
                    goal_y=tag_position[1],
                    goal_heading=_angle_desired,
                    frame_name=VISION_FRAME_NAME,
                    body_height=0.0,
                    locomotion_hint=spot_command_pb2.HINT_AUTO,
                )

                end_time = 5.0
                # Issue the command to the robot
                robot_command_client.robot_command(
                    lease=None, command=tag_cmd, end_time_secs=time.time() + end_time
                )
                # #Feedback to check and wait until the robot is in the desired position or timeout
                start_time = time.time()
                current_time = time.time()
                while (
                    not self.final_state(
                        tag_position, _angle_desired, robot_state_client
                    )
                    and current_time - start_time < end_time
                ):
                    time.sleep(0.25)
                    current_time = time.time()

                robot.power_off()

        except Exception as e:
            logger.error(f"Exception in move_to_target: {e}")
            return False

    def get_fiducial_objects(self, robot) -> world_object_pb2.WorldObject:
        """Get all fiducials that Spot detects with its perception system."""
        # Get all fiducial objects (an object of a specific type).
        request_fiducials = [world_object_pb2.WORLD_OBJECT_APRILTAG]
        world_object_client = robot.ensure_client(
            WorldObjectClient.default_service_name
        )
        fiducial_objects = world_object_client.list_world_objects(
            object_type=request_fiducials
        ).world_objects
        return fiducial_objects

    def offset_tag_pose(self, object_rt_world, robot_state_client, dist_margin=1.0):
        """Offset the go-to location of the fiducial and compute the desired heading."""
        robot_rt_world = get_vision_tform_body(
            robot_state_client.get_robot_state().kinematic_state.transforms_snapshot
        )
        robot_to_object_ewrt_world = np.array(
            [
                object_rt_world.x - robot_rt_world.x,
                object_rt_world.y - robot_rt_world.y,
                0,
            ]
        )
        robot_to_object_ewrt_world_norm = robot_to_object_ewrt_world / np.linalg.norm(
            robot_to_object_ewrt_world
        )
        heading = self.get_desired_angle(robot_to_object_ewrt_world_norm)
        goto_rt_world = np.array(
            [
                object_rt_world.x - robot_to_object_ewrt_world_norm[0] * dist_margin,
                object_rt_world.y - robot_to_object_ewrt_world_norm[1] * dist_margin,
            ]
        )
        return goto_rt_world, heading

    def get_desired_angle(self, xhat):
        """Compute heading based on the vector from robot to object."""
        zhat = [0.0, 0.0, 1.0]
        yhat = np.cross(zhat, xhat)
        mat = np.array([xhat, yhat, zhat]).transpose()
        return Quat.from_matrix(mat).to_yaw()

    def final_state(self, tag_position, desired_angle, robot_state_client):
        """Check if the current robot state is within range of the fiducial position."""
        robot_state = get_vision_tform_body(
            robot_state_client.get_robot_state().kinematic_state.transforms_snapshot
        )
        robot_angle = robot_state.rot.to_yaw()
        if tag_position.size != 0:
            x_dist = abs(tag_position[0] - robot_state.x)
            y_dist = abs(tag_position[1] - robot_state.y)
            angle = abs(desired_angle - robot_angle)
            if (x_dist < 0.05) and (y_dist < 0.05) and (angle < 0.075):
                return True
        return False


class FakeSpotRobotServiceImpl(SpotRobotServiceImpl):
    """
    Fake implementation of RobotService for testing purposes.
    """

    @override
    def move_to_target(self, robot_id: UUID, fiducial: int) -> bool:
        """
        Fake implementation of move_to_target.
        Always returns True for testing purposes.
        """
        return True
