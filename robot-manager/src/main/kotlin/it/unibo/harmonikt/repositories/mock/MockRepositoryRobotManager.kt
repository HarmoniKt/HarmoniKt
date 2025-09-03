package it.unibo.harmonikt.repositories.mock

import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.repository.RobotRepository

/**
 * Mock implementation of the RobotRepository interface for testing purposes.
 * This class simulates a repository for managing robots, allowing for registration,
 * deletion, and retrieval of robot information without a real database.
 */
class MockRepositoryRobotManager : RobotRepository {
    private val registeredRobots = mutableMapOf<RobotId, Pair<RobotType, String>>()

    override fun registerRobot(robot: RobotId, type: RobotType, canonicalName: String): Boolean {
        if (registeredRobots.containsKey(robot)) {
            return false // Robot with this ID already exists
        }
        registeredRobots[robot] = type to canonicalName
        return true // Registration successful
    }

    override fun deleteRobot(robot: RobotId): Boolean = if (registeredRobots.remove(robot) != null) {
        true // Deletion successful
    } else {
        false // No robot with this ID exists
    }

    override fun getRobots(): List<RobotInfo> = registeredRobots.map { (robotId, robot) ->
        RobotInfo(robotId, robot.second, robot.first)
    }

    override fun getRobotById(id: RobotId): RobotInfo? = getRobots().firstOrNull { it.id == id }

    override fun updateRobotPosition(id: RobotId, position: RobotPosition): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateRobotState(id: RobotId, state: RobotState): Boolean {
        TODO("Not yet implemented")
    }
}
