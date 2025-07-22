from enum import Enum
from pydantic import BaseModel, Field, ConfigDict
from uuid import UUID
from bosdyn.client import Robot as BosdynRobot


# Type alias for RobotId
RobotId = UUID


class RobotType(str, Enum):
    """
    Represents the type of robot within the system.
    Determines the category or model of the robot and guides its specific functionalities,
    such as marker type interpretation or action compatibility.
    """

    MIR = "MIR"
    SPOT = "SPOT"


class RobotState(str, Enum):
    """
    Represents the current operational state of a robot.
    Defines the possible physical activities or conditions a robot can be in.
    """

    IDLE = "IDLE"
    ON_MISSION = "ON_MISSION"
    RECHARGING = "RECHARGING"


class BatteryLevel(BaseModel):
    """
    Represents the battery level of a robot.
    The value is constrained to be between 0.0 and 100.0 percent.
    """

    value: float = Field(ge=0.0, le=100.0)


class CanonicalName(BaseModel):
    """
    Represents a standardized name format for robots in the system.
    This class encapsulates the robot's name to ensure consistent naming conventions.
    """

    name: str


class RobotPosition(BaseModel):
    """
    Represents the position of a robot in a 2D environment.
    Uses a Cartesian coordinate system with x and y coordinates.
    """

    x: int
    y: int


class Robot(BaseModel):
    """
    Represents a robot in the system.
    This class is used at the `robot service` level to model robot entities.
    """

    id: UUID
    battery_level: BatteryLevel
    current_position: RobotPosition
    current_state: RobotState
    type: RobotType


class SpotRobot(BaseModel):
    """
    Represents a Spot robot in the system.
    Inherits from the base Robot class and specifies the type as SPOT.
    """

    model_config = ConfigDict(arbitrary_types_allowed=True)

    username: str
    password: str
    address: str
    canonical_name: CanonicalName
    delegate: BosdynRobot


class MarkerBase(BaseModel):
    """
    Base class for all marker types.
    """

    id: UUID


class SpotMarker(MarkerBase):
    """
    Represents a marker specifically for Spot robots.
    Spot robots use waypoint strings to recognize markers.
    """

    waypoint: str
    marker_type: str = "SPOT"
