package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType

/**
 * Repository interface for managing Robot entities.
 * This interface defines methods for retrieving and updating robot information.
 */
interface RobotRepository {
    /**
     * Registers a new robot with the given id and type.
     *
     * @param robot the unique identifier of the robot
     * @param type the type of the robot (e.g., MIR, SPOT)
     * @return true if the registration was successful, false if a robot with the same id already exists
     */
    fun registerRobot(robot: RobotId, type: RobotType, canonicalName: String): Boolean

    /**
     * Deletes the robot with the given id.
     *
     * @param robot the unique identifier of the robot to delete
     * @return true if the deletion was successful, false if no robot with that id exists
     */
    fun deleteRobot(robot: RobotId): Boolean

    /**
     * Returns the list of all robots.
     */
    fun getRobots(): List<RobotInfo>

    /**
     * Returns the robot with the given id, or null if not found.
     *
     * @param id the unique identifier of the robot
     */
    fun getRobotById(id: RobotId): RobotInfo?
}
