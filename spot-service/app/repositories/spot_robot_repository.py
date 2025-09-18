from abc import ABC, abstractmethod
from typing import List, Optional
from uuid import UUID, uuid4
import bosdyn.client
from bosdyn.client.robot_state import RobotStateClient
from bosdyn.client.frame_helpers import get_odom_tform_body
from bosdyn.client import Robot as BosdynRobot
import numpy
from bosdyn.api import robot_state_pb2
from google.protobuf import text_format
from google.protobuf.json_format import MessageToDict, ParseDict

from app.models import (
    Robot,
    RobotPosition,
    RobotState,
    SpotRobot,
    CanonicalName,
    BatteryLevel,
)


class RobotRepository(ABC):
    """
    Abstract base class for robot repositories.
    """

    @abstractmethod
    def get_robots(self) -> List[Robot]:
        """Returns the list of all robot IDs."""
        raise NotImplementedError("This method should be implemented by subclasses.")

    @abstractmethod
    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        """Returns the robot with the given id, or None if not found."""
        raise NotImplementedError("This method should be implemented by subclasses.")

    @abstractmethod
    def get_spot_robot_delegate_by_id(self, robot_id: UUID) -> Optional[BosdynRobot]:
        """Returns the Spot robot delegate by its ID, or None if not found."""
        raise NotImplementedError("This method should be implemented by subclasses.")

    @abstractmethod
    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        """Creates a new robot and returns its ID."""
        """This method is not abstract in the original code, but it is included here for completeness."""
        raise NotImplementedError("This method should be implemented by subclasses.")

    @abstractmethod
    def delete_robot(self, robot_id: UUID) -> bool:
        """Deletes a robot by its ID and returns True if successful, False otherwise."""
        raise NotImplementedError("This method should be implemented by subclasses.")


class SpotRobotRepositoryImpl(RobotRepository):
    """
    Implementation of SpotRobotRepository.
    Stores robots in memory.
    """

    spot_robots: dict[UUID, SpotRobot] = {}

    def __fetch_status_from_spot(self, robot: BosdynRobot) -> (any, numpy.array):
        spot_robot_status_client = robot.ensure_client(
            RobotStateClient.default_service_name
        )
        spot_robot_status = spot_robot_status_client.get_robot_state()

        odom_tform_body = get_odom_tform_body(robot.get_frame_tree_snapshot())
        position = odom_tform_body.get_translation()

        return spot_robot_status, position

    def __translate_state(self, state: int) -> RobotState:
        if state == 3:
            return RobotState.IDLE
        elif state == 2 or state == 4:
            return RobotState.ON_MISSION
        elif state == 0 or state == 1:
            return RobotState.RECHARGING
            return RobotState.UNKNOWN

    def get_robots(self) -> list[Robot]:
        # We can also query all robots from the Spot SDK if needed.
        return [
            Robot(
                id=robot.id,
                canonical_name=robot.canonical_name,
                battery_level=BatteryLevel(value=0),  # Placeholder for battery level
                current_position=RobotPosition(x=0, y=0),  # Placeholder for position
                current_state=RobotState.IDLE,  # Placeholder for state
            )
            for robot in self.spot_robots.values()
        ]

    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        spot_robot = self.spot_robots.get(robot_id)
        if spot_robot is None:
            return None

        spot_robot_status, position = self.__fetch_status_from_spot(spot_robot.delegate)

        # TODO: check if methods are correct... no types :)
        robot = Robot(
            id=robot_id,
            canonical_name=spot_robot.canonical_name,
            battery_level=BatteryLevel(value=spot_robot_status.battery_states[0].charge_percentage.value),
            current_position=RobotPosition(x=position[0], y=position[1]),
            current_state=RobotState(self.__translate_state(spot_robot_status.behavior_state.state)),
        )
        return robot

    def get_spot_robot_delegate_by_id(self, robot_id: UUID) -> Optional[BosdynRobot]:
        spot_robot = self.spot_robots.get(robot_id)
        if spot_robot is None:
            return None
        return spot_robot.delegate

    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        robot_id = uuid4()

        sdk = bosdyn.client.create_standard_sdk("RobotStateClient")
        robot_delegate = sdk.create_robot(address=address)
        robot_delegate.authenticate(username, password)

        # Assuming default values for battery level and position
        new_spot_robot = SpotRobot(
            canonical_name=CanonicalName(name=canonical_name),
            username=username,
            password=password,
            address=address,
            delegate=robot_delegate,
        )

        self.spot_robots[robot_id] = new_spot_robot
        return robot_id

    def delete_robot(self, robot_id: UUID) -> bool:
        """
        Deletes a robot by its ID and returns True if successful, False otherwise.
        """
        if robot_id in self.spot_robots:
            del self.spot_robots[robot_id]
            return True
        return False


class FakeSpotRobotRepositoryImpl(RobotRepository):
    """
    Fake implementation of SpotRobotRepository for testing purposes.
    """

    spot_robots: dict[UUID, Robot] = {}

    def get_robots(self) -> List[Robot]:
        return list(self.spot_robots.values())

    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        return self.spot_robots.get(robot_id)

    def get_spot_robot_delegate_by_id(self, robot_id: UUID) -> Optional[BosdynRobot]:
        return None

    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        robot_id = uuid4()

        new_spot_robot = Robot(
            id=robot_id,
            canonical_name=CanonicalName(name=canonical_name),
            battery_level=BatteryLevel(
                value=100.0
            ),  # Default battery level for testing
            current_position=RobotPosition(x=0, y=0),  # Default position for testing
            current_state=RobotState.IDLE,
        )

        self.spot_robots[robot_id] = new_spot_robot
        return robot_id

    def delete_robot(self, robot_id: UUID) -> bool:
        """
        Deletes a robot by its ID and returns True if successful, False otherwise.
        """
        if robot_id in self.spot_robots:
            del self.spot_robots[robot_id]
            return True
        return False


class MockSpotRobotRepositoryImpl(RobotRepository):
    """
    Mock implementation of SpotRobotRepository for testing purposes.
    Uses mock data from a text proto file to simulate a Spot robot.
    """

    spot_robot_names: dict[UUID, CanonicalName] = {}
    mock_data_path: str

    def __init__(self, mock_data_path: str = "app/resources/spot-outputs/state-0.txt"):
        self.mock_data_path = mock_data_path

    def __parse_robot_state(self, text_proto: str) -> robot_state_pb2.RobotState:
        """Parses a text proto into a RobotState protobuf message."""
        msg = robot_state_pb2.RobotState()
        text_format.Parse(text_proto, msg)
        return msg

    def __parse_response(self, path: str) -> dict:
        """Reads a file, parses it into a RobotState, and converts it to a dictionary."""
        with open(path, "r") as f:
            text_proto = f.read()
        robot_state = self.__parse_robot_state(text_proto)
        robot_dict = MessageToDict(robot_state, preserving_proto_field_name=True)
        return robot_dict

    def __dict_to_textproto(self, robot_dict: dict) -> str:
        """Converts a dictionary back to a text proto."""
        msg = robot_state_pb2.RobotState()
        ParseDict(robot_dict, msg)
        text_proto = text_format.MessageToString(msg)
        return text_proto

    def __fetch_status_from_mock(self) -> dict:
        """Fetches the robot status from the mock data file."""
        return self.__parse_response(self.mock_data_path)

    def get_robots(self) -> List[Robot]:
        return [
            Robot(
                id=robot_id,
                canonical_name=canonical_name,
                battery_level=BatteryLevel(value=0),  # Placeholder for battery level
                current_position=RobotPosition(x=0, y=0),  # Placeholder for position
                current_state=RobotState.IDLE,  # Placeholder for state
            )
            for robot_id, canonical_name in self.spot_robot_names.items()
        ]

    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        """Returns the robot with the given id, or None if not found."""
        spot_robot_name = self.spot_robot_names.get(robot_id)
        if spot_robot_name is None:
            return None

        robot_status = self.__fetch_status_from_mock()

        # Extract battery percentage
        battery_percentage = 0.0
        try:
            battery_percentage = float(
                robot_status["power_state"]["locomotion_charge_percentage"]
            )
        except (KeyError, TypeError, ValueError):
            pass

        # Extract position (preferably from "vision" or "odom")
        x, y = 0.0, 0.0
        try:
            transforms = robot_status["kinematic_state"]["transforms_snapshot"][
                "child_to_parent_edge_map"
            ]
            # Prefer "vision" frame position if available
            vision_position = (
                transforms.get("vision", {})
                .get("parent_tform_child", {})
                .get("position", {})
            )
            x = float(vision_position.get("x", 0.0))
            y = float(vision_position.get("y", 0.0))
        except (KeyError, TypeError, ValueError):
            pass

        # Extract state
        state = RobotState.IDLE
        try:
            behavior_state = robot_status["behavior_state"]["state"]
            if (
                behavior_state == "STATE_STANDING"
                or behavior_state == "STATE_NOT_READY"
            ):
                state = RobotState.IDLE
            elif (
                behavior_state == "STATE_WALKING" or behavior_state == "STATE_STEPPING"
            ):
                state = RobotState.ON_MISSION
            elif behavior_state == "STATE_DOCKED":
                state = RobotState.RECHARGING
        except KeyError:
            pass

        robot = Robot(
            id=robot_id,
            canonical_name=spot_robot_name,
            battery_level=BatteryLevel(value=battery_percentage),
            current_position=RobotPosition(x=x, y=y),
            current_state=state,
        )
        return robot

    def get_spot_robot_delegate_by_id(self, robot_id: UUID) -> Optional[BosdynRobot]:
        return None

    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        """Creates a new robot and returns its ID."""
        robot_id = uuid4()
        self.spot_robot_names[robot_id] = CanonicalName(name=canonical_name)
        return robot_id

    def delete_robot(self, robot_id: UUID) -> bool:
        """Deletes a robot by its ID and returns True if successful, False otherwise."""
        if robot_id in self.spot_robot_names:
            del self.spot_robot_names[robot_id]
            return True
        return False
