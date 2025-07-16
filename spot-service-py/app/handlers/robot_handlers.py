"""
Robot Handlers Module

This module defines the handlers for robot-related HTTP endpoints in the Spot service.
These handlers process HTTP requests and generate appropriate responses for robot operations.
"""

from fastapi import APIRouter, HTTPException
from typing import List
from uuid import UUID

from ..repositories.spot_robot_repository import RobotRepository
from ..models import Robot, RobotPosition, RobotState, SpotMarker

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
    
    @router.get("/robots", response_model=List[UUID])
    async def get_robots():
        """Get all robot IDs"""
        return repository.get_robots()
    
    @router.get("/robots/{robot_id}", response_model=Robot)
    async def get_robot(robot_id: UUID):
        """Get a robot by ID"""
        robot = repository.get_robot_by_id(robot_id)
        if robot is None:
            raise HTTPException(status_code=404, detail=f"Robot with ID {robot_id} not found")
        return robot
    
    @router.get("/robots/{robot_id}/position", response_model=RobotPosition)
    async def get_robot_position(robot_id: UUID):
        """Get the position of a robot by ID"""
        robot = repository.get_robot_by_id(robot_id)
        if robot is None:
            raise HTTPException(status_code=404, detail=f"Robot with ID {robot_id} not found")
        return robot.current_position
    
    @router.post("/robots/{robot_id}/position")
    async def update_robot_position(robot_id: UUID, position: RobotPosition):
        """Update the position of a robot by ID"""
        if repository.update_robot_position(robot_id, position):
            return {"message": f"Position updated for robot with ID {robot_id}"}
        raise HTTPException(status_code=404, detail=f"Robot with ID {robot_id} not found")
    
    @router.post("/robots/{robot_id}/move")
    async def move_robot(robot_id: UUID, target: SpotMarker):
        """Move a robot to a target marker"""
        robot = repository.get_robot_by_id(robot_id)
        if robot is None:
            raise HTTPException(status_code=404, detail=f"Robot with ID {robot_id} not found")
        
        # In a real implementation, we would check if the marker exists
        # and then move the robot to the marker's position
        repository.update_robot_state(robot_id, RobotState.ON_MISSION)
        
        return {"message": f"Robot with ID {robot_id} is moving to marker {target.id}"}
    
    return router