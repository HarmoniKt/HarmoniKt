@file:Suppress("MagicNumber", "UnusedParameter")

package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.repository.RobotRepository

/**
 * Implementation of the RobotRepository interface for the Robot Manager service.
 * This class provides access to robot data in the Robot Manager service.
 */
class RobotRepositoryRobotManager : RobotRepository {
    override fun registerRobot(robot: RobotId, type: RobotType, canonicalName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteRobot(robot: RobotId): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the list of all robots.
     */
    override fun getRobots(): List<RobotInfo> = TODO()

    /**
     * Returns the robot with the given id, or null if not found.
     *
     * @param id the unique identifier of the robot
     */
    override fun getRobotById(id: RobotId): RobotInfo? = TODO()

    /**
     * Adds a new robot to the repository.
     *
     * @param robot the robot to add
     * @return true if the robot was added successfully, false otherwise
     */
    fun addRobot(robot: Robot) {
        TODO()
    }

    /**
     * Removes a robot from the repository.
     *
     * @param id the unique identifier of the robot to remove
     * @return true if the robot was removed successfully, false otherwise
     */
    fun removeRobot(id: RobotId): Boolean = TODO()
}
