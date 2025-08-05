import math
from abc import ABC, abstractmethod
from typing import override
from uuid import UUID
import logging
import time
import numpy as np

from bosdyn.api import world_object_pb2
from bosdyn.client.frame_helpers import (
    VISION_FRAME_NAME,
    get_vision_tform_body,
    get_a_tform_b,
)
from bosdyn.client.robot_command import (
    RobotCommandBuilder,
    RobotCommandClient,
    blocking_stand,
)
from bosdyn.client.world_object import WorldObjectClient
from bosdyn.client.robot_state import RobotStateClient
from bosdyn.api.geometry_pb2 import SE2Velocity, SE2VelocityLimit, Vec2
from bosdyn.api.spot import robot_command_pb2 as spot_command_pb2

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

        # Constants for movement
        self._x_eps = 0.05  # meters
        self._y_eps = 0.05  # meters
        self._angle_eps = 0.075  # radians

        # Maximum speeds
        self._max_x_vel = 0.5  # m/s
        self._max_y_vel = 0.5  # m/s
        self._max_ang_vel = 1.0  # rad/s

        # Movement settings
        self._limit_speed = True
        self._avoid_obstacles = False

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
            robot = self.robot_repository.get_spot_robot_delegate_by_id(robot_id)
            if robot is None:
                logger.error(f"Robot with ID {robot_id} not found")
                return False

            robot.start_time_sync()

            # Ensure clients
            robot_command_client = robot.ensure_client(
                RobotCommandClient.default_service_name
            )
            world_object_client = robot.ensure_client(
                WorldObjectClient.default_service_name
            )
            robot_state_client = robot.ensure_client(
                RobotStateClient.default_service_name
            )

            # Power on and stand the robot
            robot.power_on(timeout_sec=20)
            blocking_stand(robot_command_client)

            # Find the target fiducial
            request_fiducials = [world_object_pb2.WORLD_OBJECT_APRILTAG]
            fiducial_objects = world_object_client.list_world_objects(
                object_type=request_fiducials
            ).world_objects

            target_fiducial = None
            for obj in fiducial_objects:
                if obj.apriltag_properties.tag_id == fiducial:
                    target_fiducial = obj
                    break

            if target_fiducial is None:
                logger.error(f"Fiducial with ID {fiducial} not found")
                return False

            # Get fiducial transform in the VISION frame
            vision_tform_fiducial = get_a_tform_b(
                target_fiducial.transforms_snapshot,
                VISION_FRAME_NAME,
                target_fiducial.apriltag_properties.frame_name_fiducial,
            )

            # Get robot transforms in the VISION frame
            robot_state = robot_state_client.get_robot_state()
            vision_tform_body = get_vision_tform_body(
                robot_state.kinematic_state.transforms_snapshot
            )

            # Compute offset go-to point
            tag_pos = vision_tform_fiducial.translation
            object_rt_world = np.array([tag_pos.x, tag_pos.y, tag_pos.z])
            robot_rt_world = np.array(
                [vision_tform_body.x, vision_tform_body.y, vision_tform_body.z]
            )

            # Vector pointing from robot to fiducial
            vector_to_tag = object_rt_world - robot_rt_world
            vector_to_tag[2] = 0.0  # Ignore z
            norm = np.linalg.norm(vector_to_tag)
            if norm == 0:
                logger.warning("Robot is already at the fiducial.")
                return True

            direction = vector_to_tag / norm
            # Offset the target point by stopping distance
            stop_margin = 0.5
            goto_point = object_rt_world[:2] - direction[:2] * stop_margin

            # Compute heading angle (yaw)
            desired_yaw = math.atan2(direction[1], direction[0])

            # Set mobility parameters
            speed_limit = SE2VelocityLimit(
                max_vel=SE2Velocity(
                    linear=Vec2(x=self._max_x_vel, y=self._max_y_vel),
                    angular=self._max_ang_vel,
                )
            )

            mobility_params = spot_command_pb2.MobilityParams(
                vel_limit=speed_limit, locomotion_hint=spot_command_pb2.HINT_AUTO
            )

            if not self._avoid_obstacles:
                mobility_params.obstacle_params.CopyFrom(
                    spot_command_pb2.ObstacleParams(
                        disable_vision_body_obstacle_avoidance=True,
                        disable_vision_foot_obstacle_avoidance=True,
                        disable_vision_foot_constraint_avoidance=True,
                    )
                )

            # Build and send command
            command = RobotCommandBuilder.synchro_se2_trajectory_point_command(
                goal_x=goto_point[0],
                goal_y=goto_point[1],
                goal_heading=desired_yaw,
                frame_name=VISION_FRAME_NAME,
                params=mobility_params,
                body_height=0.0,
            )

            end_time = 10.0
            robot_command_client.robot_command(
                command, end_time_secs=time.time() + end_time
            )

            # Wait for the robot to arrive
            start_time = time.time()
            while time.time() - start_time < end_time:
                state = robot_state_client.get_robot_state()
                vision_tform_body = get_vision_tform_body(
                    state.kinematic_state.transforms_snapshot
                )
                dx = abs(goto_point[0] - vision_tform_body.x)
                dy = abs(goto_point[1] - vision_tform_body.y)
                dyaw = abs(desired_yaw - vision_tform_body.rot.to_yaw())

                if dx < self._x_eps and dy < self._y_eps and dyaw < self._angle_eps:
                    logger.info("Robot reached the target position.")
                    return True

                time.sleep(0.2)

            logger.warning("Timed out waiting for robot to reach target.")
            return False

        except Exception as e:
            logger.error(f"Exception in move_to_target: {e}")
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
