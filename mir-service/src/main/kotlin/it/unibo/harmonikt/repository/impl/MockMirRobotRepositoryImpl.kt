package it.unibo.harmonikt.repository.impl

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType

/**
 * Fake implementation of MirRobotRepository using an in-memory list and an HTTP client.
 */
class MockMirRobotRepositoryImpl : AbstractMirRobotRepository() {
    override suspend fun getRobotById(id: RobotId): Robot? {
        val mirRobot = robots.find { it.id == id } ?: return null
        return Robot(
            id = mirRobot.id,
            name = mirRobot.name,
            batteryLevel = BatteryLevel(100.0),
            currentPosition = RobotPosition(0.0, 0.0),
            currentState = RobotState.IDLE,
            type = RobotType.MIR,
        )
    }
}
