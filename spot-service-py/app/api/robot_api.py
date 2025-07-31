from uuid import UUID

from pydantic import BaseModel
from app.models import RobotPosition, Robot


class RobotRegistrationDTO(BaseModel):
    """
    Represents a request to create a new Spot robot.
    This model is used for validating the request body when creating a new Spot robot.
    """

    username: str
    password: str
    host: str
    canonicalName: str

class RobotIdDTO(BaseModel):
    """
    Represents a request to create a new Spot robot with only the ID.
    This model is used for validating the request body when creating a new Spot robot.
    """

    id: UUID


class RobotInfoDTO(BaseModel):
    """
    Represents the basic information of a robot.
    This model is used to return the robot's ID and canonical name.
    """

    id: UUID
    canonicalName: str

    @staticmethod
    def from_robot(robot: Robot) -> "RobotInfoDTO":
        """
        Converts a Robot object to a RobotInfoDTO object.

        Args:
            robot: The Robot object to convert

        Returns:
            A RobotInfoDTO object containing the robot's ID and canonical name
        """
        return RobotInfoDTO(id=robot.id, canonicalName=robot.canonical_name.name)


class RobotStatusDTO(BaseModel):
    id: UUID
    canonicalName: str
    state: str
    batteryLevel: float
    currentPosition: RobotPosition
    type: str

    @staticmethod
    def from_robot(robot: Robot) -> "RobotStatusDTO":
        """
        Converts a Robot object to a RobotStatusDTO object.

        Args:
            robot: The Robot object to convert

        Returns:
            A RobotStatusDTO object containing the robot's status information
        """
        return RobotStatusDTO(
            id=robot.id,
            canonicalName=robot.canonical_name.name,
            state=robot.current_state.value,
            batteryLevel=robot.battery_level.value,
            currentPosition=robot.current_position,
            type="SPOT",
        )
