from abc import ABC, abstractmethod
from typing import List, Optional
from uuid import UUID, uuid4

from app.models import SpotMarker

class MarkerRepository(ABC):
    """
    Abstract base class for marker repositories.
    """
    @abstractmethod
    def get_markers(self) -> List[SpotMarker]:
        """Returns the list of all markers."""
        pass

    @abstractmethod
    def get_marker_by_id(self, marker_id: UUID) -> Optional[SpotMarker]:
        """Returns the marker with the given id, or None if not found."""
        pass

    @abstractmethod
    def create_marker(self, marker: SpotMarker) -> None:
        """Adds a new marker to the repository."""
        pass

    @abstractmethod
    def delete_marker(self, marker_id: UUID) -> bool:
        """Deletes the marker with the given id. Returns True if successful, False otherwise."""
        pass


class FakeSpotMarkerRepository(MarkerRepository):
    """
    Fake implementation of SpotMarkerRepository for testing purposes.
    Stores markers in memory.
    """
    def __init__(self):
        self.markers = [
            SpotMarker(id=uuid4(), waypoint="Waypoint 1"),
            SpotMarker(id=uuid4(), waypoint="Waypoint 2"),
            SpotMarker(id=uuid4(), waypoint="Waypoint 3"),
        ]

    def get_markers(self) -> List[SpotMarker]:
        """Returns the list of all spot markers."""
        return self.markers

    def get_marker_by_id(self, marker_id: UUID) -> Optional[SpotMarker]:
        """Returns the spot marker with the given id, or None if not found."""
        return next((marker for marker in self.markers if marker.id == marker_id), None)

    def create_marker(self, marker: SpotMarker) -> None:
        """
        Adds a new spot marker to the repository.
        Raises ValueError if a marker with the same id already exists.
        """
        if self.get_marker_by_id(marker.id) is not None:
            raise ValueError(f"Marker with id {marker.id} already exists.")
        self.markers.append(marker)

    def delete_marker(self, marker_id: UUID) -> bool:
        """
        Deletes the spot marker with the given id.
        Returns True if the marker was removed, False otherwise.
        """
        marker = self.get_marker_by_id(marker_id)
        if marker:
            self.markers.remove(marker)
            return True
        return False
