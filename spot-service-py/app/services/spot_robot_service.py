from abc import ABC, abstractmethod
from typing import override
from uuid import UUID
import logging

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

    @override
    def move_to_target(self, robot_id: UUID, fiducial: int) -> bool:
        pass


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
