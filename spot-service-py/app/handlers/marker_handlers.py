"""
Marker Handlers Module

This module defines the handlers for marker-related HTTP endpoints in the Spot service.
These handlers process HTTP requests and generate appropriate responses for marker operations.
"""

from fastapi import APIRouter, HTTPException, status
from typing import List
from uuid import UUID
import logging

from app.repositories.spot_marker_repository import MarkerRepository
from app.models import SpotMarker

# Create a router for marker endpoints
router = APIRouter()


def setup_marker_handlers(repository: MarkerRepository):
    """
    Sets up the routing for marker-related endpoints in the Spot service.

    Args:
        repository: The repository for accessing marker data

    Returns:
        The configured router
    """

    @router.get("/markers", response_model=List[SpotMarker])
    async def get_markers():
        """Get all markers"""
        logging.info("Getting all markers")
        pass

    @router.post("/markers", status_code=status.HTTP_201_CREATED)
    async def create_marker(marker: SpotMarker):
        """Create a new marker"""
        try:
            repository.create_marker(marker)
            return {"message": f"Marker created with ID {marker.id}"}
        except ValueError as e:
            raise HTTPException(status_code=400, detail=str(e))

    @router.get("/markers/{marker_id}", response_model=SpotMarker)
    async def get_marker(marker_id: UUID):
        """Get a marker by ID"""
        marker = repository.get_marker_by_id(marker_id)
        if marker is None:
            raise HTTPException(
                status_code=404, detail=f"Marker with ID {marker_id} not found"
            )
        return marker

    @router.delete("/markers/{marker_id}")
    async def delete_marker(marker_id: UUID):
        """Delete a marker by ID"""
        if repository.delete_marker(marker_id):
            return {"message": f"Marker with ID {marker_id} deleted"}
        raise HTTPException(
            status_code=404, detail=f"Marker with ID {marker_id} not found"
        )

    @router.get("/markers/{marker_id}/position")
    async def get_marker_position(marker_id: UUID):
        """Get the position of a marker by ID"""
        marker = repository.get_marker_by_id(marker_id)
        if marker is None:
            raise HTTPException(
                status_code=404, detail=f"Marker with ID {marker_id} not found"
            )
        return {"marker_id": marker_id, "waypoint": marker.waypoint}

    return router
