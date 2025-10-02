@file:Suppress("MagicNumber", "UnusedPrivateMember")

package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.MirRobot
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo

/**
 * Repository interface for managing MirRobot entities.
 * This interface extends the generic RobotRepository to handle Mir-specific robots.
 */
interface MirRobotRepository {
    /**
     * Creates a new Mir robot with the given canonical name, API token, and host.
     * @param canonicalName The canonical name of the robot.
     * @param apiToken The API token for the robot.
     * @param host The host address of the robot.
     * @return The unique identifier of the created robot.
     */
    suspend fun createRobot(canonicalName: String, apiToken: String, host: String): RobotId?

    /**
     * Deletes the specified Mir robot.
     * @param robot The unique identifier of the robot to delete.
     * @return True if the robot was deleted, false otherwise.
     */
    suspend fun deleteRobot(robot: RobotId): Boolean

    /**
     * Retrieves a list of all Mir robots.
     * @return A list of robot information objects.
     */
    suspend fun getRobots(): List<RobotInfo>

    /**
     * Retrieves a Mir robot by its unique identifier.
     * @param id The unique identifier of the robot.
     * @return The robot object if found, or null otherwise.
     */
    suspend fun getRobotById(id: RobotId): Robot?

    /**
     * Retrieves a Mir robot by its unique identifier.
     * @param id The unique identifier of the robot.
     * @return The Mir robot object if found, or null otherwise.
     */
    fun getMirRobotById(id: RobotId): MirRobot?
}
