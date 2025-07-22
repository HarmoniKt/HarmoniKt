from abc import ABC, abstractmethod
from typing import List, Optional
from uuid import UUID, uuid4
import bosdyn.client

from app.models import Robot, RobotPosition, RobotState, SpotRobot, CanonicalName


class RobotRepository(ABC):
    """
    Abstract base class for robot repositories.
    """

    @abstractmethod
    def get_robots(self) -> List[UUID]:
        """Returns the list of all robot IDs."""
        pass

    @abstractmethod
    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        """Returns the robot with the given id, or None if not found."""
        pass

    @abstractmethod
    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        """Creates a new robot and returns its ID."""
        """This method is not abstract in the original code, but it is included here for completeness."""
        raise NotImplementedError("This method should be implemented by subclasses.")

    @abstractmethod
    def update_robot_position(self, robot_id: UUID, position: RobotPosition) -> bool:
        """Updates the position of the robot with the given id. Returns True if successful, False otherwise."""
        pass

    @abstractmethod
    def update_robot_state(self, robot_id: UUID, state: RobotState) -> bool:
        """Updates the state of the robot with the given id. Returns True if successful, False otherwise."""
        pass


class SpotRobotRepositoryImpl(RobotRepository):
    """
    Implementation of SpotRobotRepository.
    Stores robots in memory.
    """

    spot_robot_info: dict[UUID, SpotRobot] = {}
    spot_robot_status: dict[UUID, Robot] = {}

    def get_robots(self) -> list[tuple[UUID, str]]:
        """Returns the list of all robot IDs."""
        return [
            (id, robot.canonical_name) for id, robot in self.spot_robot_info.items()
        ]

    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        """Returns the robot with the given id, or None if not found."""
        return next((robot for robot in self.robots if robot.id == robot_id), None)

    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        """
        Creates a new robot and returns its ID.
        If the robot already exists, raises a ValueError.
        """
        # Generate a new UUID for the robot
        robot_id = uuid4()

        sdk = bosdyn.client.create_standard_sdk("RobotStateClient")
        robot_delegate = sdk.create_robot(address=address)
        robot_delegate.authenticate(username, password)

        new_spot_robot = SpotRobot(
            username=username,
            password=password,
            address=address,
            canonical_name=CanonicalName(name=canonical_name),
            delegate=robot_delegate,
        )

        self.spot_robot_info[robot_id] = new_spot_robot
        return robot_id

    def update_robot_position(self, robot_id: UUID, position: RobotPosition) -> bool:
        """
        Updates the position of the robot with the given id.
        Returns True if successful, False otherwise.
        """
        robot = self.get_robot_by_id(robot_id)
        if robot:
            index = self.robots.index(robot)
            self.robots[index] = Robot(
                id=robot.id,
                name=robot.name,
                battery_level=robot.battery_level,
                current_position=position,
                current_state=robot.current_state,
                type=robot.type,
            )
            return True
        return False

    def update_robot_state(self, robot_id: UUID, state: RobotState) -> bool:
        """
        Updates the state of the robot with the given id.
        Returns True if successful, False otherwise.
        """
        robot = self.get_robot_by_id(robot_id)
        if robot:
            index = self.robots.index(robot)
            self.robots[index] = Robot(
                id=robot.id,
                name=robot.name,
                battery_level=robot.battery_level,
                current_position=robot.current_position,
                current_state=state,
                type=robot.type,
            )
            return True
        return False
