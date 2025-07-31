@file:Suppress("MagicNumber")

package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import kotlin.uuid.Uuid

/**
 * Repository interface for managing Robot entities.
 */
interface SpotRobotRepository : RobotRepository

/**
 * Fake implementation of SpotRobotRepository for testing purposes.
 * Stores robots in memory.
 */
class FakeSpotRobotRepository : SpotRobotRepository {
    private val robots = mutableListOf(
        Robot(
            id = Uuid.random(),
            name = "Spot 1",
            batteryLevel = BatteryLevel(85.0),
            currentPosition = RobotPosition(10.0, 20.0),
            currentState = RobotState.IDLE,
            type = RobotType.SPOT,
        ),
        Robot(
            id = Uuid.random(),
            name = "Spot 2",
            batteryLevel = BatteryLevel(70.0),
            currentPosition = RobotPosition(30.0, 40.0),
            currentState = RobotState.ON_MISSION,
            type = RobotType.SPOT,
        ),
        Robot(
            id = Uuid.random(),
            name = "Spot 3",
            batteryLevel = BatteryLevel(25.0),
            currentPosition = RobotPosition(50.0, 60.0),
            currentState = RobotState.RECHARGING,
            type = RobotType.SPOT,
        ),
    )

    override fun getRobots(): List<RobotInfo> = TODO()

    override fun getRobotById(id: RobotId): Robot? = robots.find { it.id == id }

    override fun updateRobotPosition(id: RobotId, position: RobotPosition): Boolean {
        val robot = getRobotById(id) ?: return false
        val index = robots.indexOf(robot)
        robots[index] = robot.copy(currentPosition = position)
        return true
    }

    override fun updateRobotState(id: RobotId, state: RobotState): Boolean {
        val robot = getRobotById(id) ?: return false
        val index = robots.indexOf(robot)
        robots[index] = robot.copy(currentState = state)
        return true
    }
}
