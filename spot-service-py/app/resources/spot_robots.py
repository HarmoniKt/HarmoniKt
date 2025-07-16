"""
Spot Robots Resource Module

This module defines the resource structure for robots in the Spot service.
In FastAPI, we don't need to define resource classes like in Ktor, but we can
define the URL paths and route structures here for documentation purposes.
"""

from uuid import UUID

# Import models using relative imports
from ..models import SpotMarker

# Define the URL paths for the robot resources
ROBOTS_PATH = "/robots"
ROBOT_ID_PATH = "/robots/{robot_id}"
ROBOT_POSITION_PATH = "/robots/{robot_id}/position"
ROBOT_MOVE_PATH = "/robots/{robot_id}/move"

# Define the route parameters for documentation
ROBOT_ID_PARAM = {
    "robot_id": {
        "description": "The unique identifier of the robot",
        "type": UUID
    }
}

# Define the request body schemas for documentation
ROBOT_MOVE_BODY = {
    "target": {
        "description": "The target marker where the robot should be moved",
        "type": SpotMarker
    }
}

class SpotRobots:
    """
    Represents the SpotRobots resource in the Spot service.

    This resource is used to manage robots within the Spot service,
    allowing for operations such as retrieving, moving, and managing
    specific robots identified by their unique IDs.
    """
    
    class Id:
        """
        Represents a specific robot identified by its ID.

        This nested resource allows for operations on a specific robot
        by its unique identifier.
        """
        
        class Position:
            """
            Represents the position of a specific robot.

            This nested resource allows for operations related to the position
            of a specific robot identified by its ID.
            """
            pass
        
        class Move:
            """
            Represents the action of moving a robot to a specific target marker.

            This nested resource allows for moving a robot to a target marker
            identified by its unique SpotMarker.
            """
            pass