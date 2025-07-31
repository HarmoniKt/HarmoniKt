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
    fun queueAction(robotId: RobotId, action: Action): Boolean
}
