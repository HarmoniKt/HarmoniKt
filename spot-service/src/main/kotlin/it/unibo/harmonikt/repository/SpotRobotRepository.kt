package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import kotlin.uuid.Uuid

interface SpotRobotRepository {
    fun getRobots(): List<Robot>
    fun getRobotById(id: Uuid): Robot?
    fun updateRobotPosition(id: Uuid, position: RobotPosition): Boolean
    fun updateRobotState(id: Uuid, state: RobotState): Boolean
}

class FakeSpotRobotRepository : SpotRobotRepository {
    private val robots = mutableListOf(
        Robot(
            id = Uuid.random(),
            name = CanonicalName("Spot 1"),
            batteryLevel = BatteryLevel(85.0),
            currentPosition = RobotPosition(10, 20),
            currentState = RobotState.IDLE,
        ),
        Robot(
            id = Uuid.random(),
            name = CanonicalName("Spot 2"),
            batteryLevel = BatteryLevel(70.0),
            currentPosition = RobotPosition(30, 40),
            currentState = RobotState.ON_MISSION,
        ),
        Robot(
            id = Uuid.random(),
            name = CanonicalName("Spot 3"),
            batteryLevel = BatteryLevel(25.0),
            currentPosition = RobotPosition(50, 60),
            currentState = RobotState.RECHARGING,
        ),
    )

    override fun getRobots(): List<Robot> = robots

    override fun getRobotById(id: Uuid): Robot? = robots.find { it.id == id }

    override fun updateRobotPosition(id: Uuid, position: RobotPosition): Boolean {
        val robot = getRobotById(id) ?: return false
        val index = robots.indexOf(robot)
        robots[index] = robot.copy(currentPosition = position)
        return true
    }

    override fun updateRobotState(id: Uuid, state: RobotState): Boolean {
        val robot = getRobotById(id) ?: return false
        val index = robots.indexOf(robot)
        robots[index] = robot.copy(currentState = state)
        return true
    }
}
