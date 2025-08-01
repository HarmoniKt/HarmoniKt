package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.RobotId

/**
 * Repository interface for managing robot actions.
 * This interface defines the contract for storing, retrieving, and managing
 * actions that robots can perform in the system.
 *
 * Currently, this is a marker interface with no methods defined.
 * Implementations will provide specific functionality based on the robot type.
 */
interface ActionRepository {
    /**
     * Retrieves all actions associated with a specific robot.
     *
     * @param robotId The unique identifier of the robot whose actions are to be retrieved.
     * @return A list of actions associated with the specified robot.
     */
    fun queueAction(robotId: RobotId, action: Action): Boolean
}
