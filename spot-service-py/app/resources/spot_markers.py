"""
Spot Markers Resource Module

This module defines the resource structure for markers in the Spot service.
In FastAPI, we don't need to define resource classes like in Ktor, but we can
define the URL paths and route structures here for documentation purposes.
"""

from uuid import UUID

# Define the URL paths for the marker resources
MARKERS_PATH = "/markers"
MARKER_ID_PATH = "/markers/{marker_id}"
MARKER_POSITION_PATH = "/markers/{marker_id}/position"

# Define the route parameters for documentation
MARKER_ID_PARAM = {
    "marker_id": {"description": "The unique identifier of the marker", "type": UUID}
}


class SpotMarkers:
    """
    Represents the SpotMarkers resource in the Spot service.

    This resource is used to manage markers within the Spot service,
    allowing for operations such as retrieving, creating, and managing
    specific markers identified by their unique IDs.
    """

    class Id:
        """
        Represents a specific marker identified by its ID.

        This nested resource allows for operations on a specific marker
        by its unique identifier.
        """

        class Position:
            """
            Represents the position of a specific marker.

            This nested resource allows for operations related to the position
            of a specific marker identified by its ID.
            """

            pass
