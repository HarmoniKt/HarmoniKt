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
            name = "Mir 1",
            batteryLevel = BatteryLevel(90.0),
            currentPosition = RobotPosition(15.0, 25.0),
            currentState = RobotState.IDLE,
            type = RobotType.MIR,
        ),
        Robot(
            id = Uuid.random(),
            name = "Mir 2",
            batteryLevel = BatteryLevel(60.0),
            currentPosition = RobotPosition(35.0, 45.0),
            currentState = RobotState.ON_MISSION,
            type = RobotType.MIR,
        ),
    )

    override fun registerRobot(robot: RobotId, type: RobotType, canonicalName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteRobot(robot: RobotId): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRobots(): List<RobotInfo> = TODO()

    override fun getRobotById(id: RobotId): RobotType? = TODO()

    override fun updateRobotPosition(id: RobotId, position: RobotPosition): Boolean = TODO()

    override fun updateRobotState(id: RobotId, state: RobotState): Boolean = TODO()
}
