package it.unibo.harmonikt.repositories.mock

import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.repository.RobotRepository

class MockRepositoryRobotManager : RobotRepository {
    private val registeredRobots = mutableMapOf<RobotId, RobotType>()
    override fun registerRobot(robot: RobotId, type: RobotType): Boolean {
        if (registeredRobots.containsKey(robot)) {
            return false // Robot with this ID already exists
        }
        registeredRobots[robot] = type
        return true // Registration successful
    }

    override fun getRobots(): List<RobotId> {
        TODO("Not yet implemented")
    }

    override fun getRobotById(id: RobotId): Robot? {
        TODO("Not yet implemented")
    }

    override fun updateRobotPosition(id: RobotId, position: RobotPosition): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateRobotState(id: RobotId, state: RobotState): Boolean {
        TODO("Not yet implemented")
    }
}
