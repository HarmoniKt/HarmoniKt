package it.unibo.harmonikt.api

import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotType
import kotlinx.serialization.Serializable

/**
 * Interface defining the Robot Management API.
 * This interface provides methods for managing robots in the system.
 */
interface RobotAPI {
    /**
     * Retrieves all active robots in the system.
     *
     * @return A list of all active robots Uuids.
     */
    fun getAllRobots(): List<RobotId>

    /**
     * Retrieves information about a specific robot.
     *
     * @param robotId The unique identifier of the robot.
     * @return The robot with the specified ID, or null if not found.
     */
    fun getRobotById(robotId: RobotId): Robot?

    /**
     * Creates a new robot in the system based on the provided request.
     *
     * @param request The details required to create the robot, including its name and type.
     * @return The unique identifier of the newly created robot.
     */
    fun createRobot(request: RobotCreationRequest): RobotId

    /**
     * Removes a robot from the system.
     *
     * @param robotId The unique identifier of the robot to remove.
     * @return True if the robot was successfully removed, false otherwise.
     */
    fun deleteRobot(robotId: RobotId): Boolean

    /**
     * Creates a new action for a specific robot.
     *
     * @param robotId The unique identifier of the robot.
     * @param action The command to execute.
     * @return The ID of the newly created action, or null if the robot was not found.
     */
    fun createRobotAction(robotId: RobotId, action: Action): RobotId?
}

/**
 * Represents the request data required to create a new robot in the system.
 *
 * @property name The desired name of the robot to be created.
 * @property type The type of the robot, determining its category (e.g., MIR, SPOT).
 */
@Serializable
data class RobotCreationRequest(val name: String, val type: RobotType)
