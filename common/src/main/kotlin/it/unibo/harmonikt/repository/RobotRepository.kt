package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import kotlin.uuid.Uuid

/**
 * Repository interface for managing Robot entities.
 * This interface defines methods for retrieving and updating robot information.
 */
interface RobotRepository {
    /**
     * Returns the list of all robots.
     */
    fun getRobots(): List<Robot>

    /**
     * Returns the robot with the given id, or null if not found.
     *
     * @param id the unique identifier of the robot
     */
    fun getRobotById(id: Uuid): Robot?

    /**
     * Updates the position of the robot with the given id.
     *
     * @param id the unique identifier of the robot
     * @param position the new position to set
     * @return true if the update was successful, false otherwise
     */
    fun updateRobotPosition(id: Uuid, position: RobotPosition): Boolean

    /**
     * Updates the state of the robot with the given id.
     *
     * @param id the unique identifier of the robot
     * @param state the new state to set
     * @return true if the update was successful, false otherwise
     */
    fun updateRobotState(id: Uuid, state: RobotState): Boolean
}
