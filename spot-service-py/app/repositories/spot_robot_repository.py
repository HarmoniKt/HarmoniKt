from abc import ABC, abstractmethod
from typing import List, Optional
from uuid import UUID, uuid4
import bosdyn.client

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

    def get_robots(self) -> list[Robot]:
        return list(self.spot_robots.values())

    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        return self.spot_robots.get(robot_id)

    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        robot_id = uuid4()

        sdk = bosdyn.client.create_standard_sdk("RobotStateClient")
        robot_delegate = sdk.create_robot(address=address)
        robot_delegate.authenticate(username, password)

        # Assuming default values for battery level and position
        new_spot_robot = SpotRobot(
            id=robot_id,
            canonical_name=CanonicalName(name=canonical_name),
            battery_level=BatteryLevel(value=0.0),
            current_position=RobotPosition(x=0, y=0),
            current_state=RobotState.IDLE,
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
    """

    def get_robots(self) -> List[Robot]:
        raise NotImplementedError("This method should be implemented by subclasses.")

    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        raise NotImplementedError("This method should be implemented by subclasses.")

    def create_robot(
        self, username: str, password: str, address: str, canonical_name: str
    ) -> UUID:
        raise NotImplementedError("This method should be implemented by subclasses.")

    def delete_robot(self, robot_id: UUID) -> bool:
        raise NotImplementedError("This method should be implemented by subclasses.")
