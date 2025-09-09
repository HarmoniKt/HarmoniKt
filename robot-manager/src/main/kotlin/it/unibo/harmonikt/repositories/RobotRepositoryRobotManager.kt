@file:Suppress("MagicNumber", "UnusedParameter")

package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.repository.RobotRepository
import java.util.concurrent.ConcurrentHashMap

/**
 * Implementation of the RobotRepository interface for the Robot Manager service.
 * This class provides access to robot data in the Robot Manager service.
 */
class RobotRepositoryRobotManager : RobotRepository {
    private val robots: MutableMap<RobotId, Robot> = ConcurrentHashMap()

    override fun registerRobot(robot: RobotId, type: RobotType, canonicalName: String): Boolean {
        if (robots.containsKey(robot)) return false
        val newRobot = Robot(
            id = robot,
            name = canonicalName,
            batteryLevel = BatteryLevel(100.0),
            currentPosition = RobotPosition(0.0, 0.0),
            currentState = RobotState.IDLE,
            type = type,
        )
        robots[robot] = newRobot
        return true
    }

    override fun deleteRobot(robot: RobotId): Boolean = robots.remove(robot) != null

    override fun getRobots(): List<RobotInfo> = robots.values.map { it.toInfo() }

    override fun getRobotById(id: RobotId): RobotInfo? = robots[id]?.toInfo()

    private fun Robot.toInfo(): RobotInfo = RobotInfo(
        id = this.id,
        canonicalName = this.name,
        type = this.type,
    )
}
