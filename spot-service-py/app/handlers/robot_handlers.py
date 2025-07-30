"""
Robot Handlers Module

This module defines the handlers for robot-related HTTP endpoints in the Spot service.
These handlers process HTTP requests and generate appropriate responses for robot operations.
"""

from fastapi import APIRouter, HTTPException
from typing import List, Dict
from uuid import UUID

from app.repositories.spot_robot_repository import RobotRepository
from app.models import Robot
from app.api.robot_api import (
    SpotRobotCreationRequest,
    RobotInfoDTO,
    RobotStatusDTO,
)

# Create a router for robot endpoints
router = APIRouter()


def setup_robot_handlers(repository: RobotRepository):
    """
    Sets up the routing for robot-related endpoints in the Spot service.

    Args:
        repository: The repository for accessing robot data

    Returns:
        The configured router
    """

    @router.get("/robots", response_model=List[RobotInfoDTO])
    async def get_robots():
        """Get all robot IDs"""
        return [RobotInfoDTO.from_robot(robot) for robot in repository.get_robots()]

    @router.post("/robots", response_model=UUID)
    async def create_robot(robot_creation_request: SpotRobotCreationRequest):
        """Create a new robot"""
        try:
            robot_id = repository.create_robot(
                username=robot_creation_request.username,
                password=robot_creation_request.password,
                address=robot_creation_request.address,
                canonical_name=robot_creation_request.canonical_name,
            )
            return robot_id
        except ValueError as e:
            raise HTTPException(status_code=400, detail=str(e))

    @router.get("/robots/{robot_id}", response_model=Robot)
    async def get_robot(robot_id: UUID):
        """Get a robot by ID"""
        robot = repository.get_robot_by_id(robot_id)
        if robot is None:
            raise HTTPException(
                status_code=404, detail=f"Robot with ID {robot_id} not found"
            )
        return RobotStatusDTO.from_robot(robot)

    @router.delete("/robots/{robot_id}", response_model=Dict[str, str])
    async def delete_robot(robot_id: UUID):
        """Delete a robot by ID"""
        if not repository.delete_robot(robot_id):
            raise HTTPException(
                status_code=404, detail=f"Robot with ID {robot_id} not found"
            )
        return {"message": f"Robot with ID {robot_id} deleted successfully"}

    return router
