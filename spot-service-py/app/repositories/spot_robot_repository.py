from abc import ABC, abstractmethod
from typing import List, Optional
from uuid import UUID, uuid4

from ..models import Robot, RobotPosition, RobotState, RobotType, CanonicalName, BatteryLevel

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
    def update_robot_position(self, robot_id: UUID, position: RobotPosition) -> bool:
        """Updates the position of the robot with the given id. Returns True if successful, False otherwise."""
        pass

    @abstractmethod
    def update_robot_state(self, robot_id: UUID, state: RobotState) -> bool:
        """Updates the state of the robot with the given id. Returns True if successful, False otherwise."""
        pass


class FakeSpotRobotRepository(RobotRepository):
    """
    Fake implementation of SpotRobotRepository for testing purposes.
    Stores robots in memory.
    """
    def __init__(self):
        self.robots = [
            Robot(
                id=uuid4(),
                name=CanonicalName(name="Spot 1"),
                battery_level=BatteryLevel(value=85.0),
                current_position=RobotPosition(x=10, y=20),
                current_state=RobotState.IDLE,
                type=RobotType.SPOT,
            ),
            Robot(
                id=uuid4(),
                name=CanonicalName(name="Spot 2"),
                battery_level=BatteryLevel(value=70.0),
                current_position=RobotPosition(x=30, y=40),
                current_state=RobotState.ON_MISSION,
                type=RobotType.SPOT,
            ),
            Robot(
                id=uuid4(),
                name=CanonicalName(name="Spot 3"),
                battery_level=BatteryLevel(value=25.0),
                current_position=RobotPosition(x=50, y=60),
                current_state=RobotState.RECHARGING,
                type=RobotType.SPOT,
            ),
        ]

    def get_robots(self) -> List[UUID]:
        """Returns the list of all robot IDs."""
        return [robot.id for robot in self.robots]

    def get_robot_by_id(self, robot_id: UUID) -> Optional[Robot]:
        """Returns the robot with the given id, or None if not found."""
        return next((robot for robot in self.robots if robot.id == robot_id), None)

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