"""
Robot Handlers Module

This module defines the handlers for robot-related HTTP endpoints in the Spot service.
These handlers process HTTP requests and generate appropriate responses for robot operations.
"""

from fastapi import APIRouter, HTTPException, status
from typing import List, Dict
from uuid import UUID
from pydantic import BaseModel
from typing import Union

from app.repositories.spot_robot_repository import RobotRepository
from app.api.robot_api import (
    RobotRegistrationDTO,
    RobotInfoDTO,
    RobotStatusDTO,
    RobotIdDTO,
)
from app.services.spot_robot_service import RobotService

# Create a router for robot endpoints
router = APIRouter()


def setup_robot_handlers(repository: RobotRepository, service: RobotService):
    """
    Sets up the routing for robot-related endpoints in the Spot service.

    Args:
        repository: The repository for accessing robot data
        service: The service for handling robot actions

    Returns:
        The configured router
    """

    @router.get("/robots", response_model=List[RobotInfoDTO])
    async def get_robots():
        """Get all robot IDs"""
        return [RobotInfoDTO.from_robot(robot) for robot in repository.get_robots()]

    @router.post("/robots", response_model=RobotIdDTO)
    async def create_robot(robot_creation_request: RobotRegistrationDTO):
        """Create a new robot"""
        try:
            robot_id = repository.create_robot(
                username=robot_creation_request.username,
                password=robot_creation_request.password,
                address=robot_creation_request.host,
                canonical_name=robot_creation_request.canonicalName,
            )
            return RobotIdDTO(id=robot_id)
        except ValueError as e:
            raise HTTPException(status_code=400, detail=str(e))

    @router.get("/robots/{robot_id}", response_model=RobotStatusDTO)
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

    @router.post("/robots/{robot_id}/actions", status_code=status.HTTP_201_CREATED)
    async def create_marker(robot_id: UUID, action: ActionDTO):
        """Create a new action for a robot"""
        try:
            if isinstance(action, MoveToTargetDTO):
                service.move_to_target(
                    robot_id=robot_id,
                    fiducial=action.fiducial,
                )
            else:
                raise HTTPException(status_code=400, detail="Unsupported action type")
            return {"message": f"Creation for robot {robot_id}"}
        except ValueError as e:
            raise HTTPException(status_code=400, detail=str(e))

    return router


type ActionDTO = Union[MoveToTargetDTO]


class MoveToTargetDTO(BaseModel):
    type: str = "MoveToTarget"
    fiducial: int
