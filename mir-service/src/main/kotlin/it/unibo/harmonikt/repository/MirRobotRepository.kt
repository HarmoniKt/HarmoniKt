@file:Suppress("MagicNumber")

package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import kotlin.uuid.Uuid

/**
 * Repository interface for managing MirRobot entities.
 * This interface extends the generic RobotRepository to handle Mir-specific robots.
 */
interface MirRobotRepository : RobotRepository

/**
 * Fake implementation of MirRobotRepository for testing purposes.
 * Stores robots in memory.
 */
class FakeMirRobotRepository : MirRobotRepository {
    private val robots = mutableListOf(
        Robot(
            id = Uuid.random(),
            name = CanonicalName("Mir 1"),
            batteryLevel = BatteryLevel(90.0),
            currentPosition = RobotPosition(15, 25),
            currentState = RobotState.IDLE,
            type = RobotType.MIR,
        ),
        Robot(
            id = Uuid.random(),
            name = CanonicalName("Mir 2"),
            batteryLevel = BatteryLevel(60.0),
            currentPosition = RobotPosition(35, 45),
            currentState = RobotState.ON_MISSION,
            type = RobotType.MIR,
        ),
    )

    override fun getRobots(): List<RobotId> = robots.map { it.id }

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
