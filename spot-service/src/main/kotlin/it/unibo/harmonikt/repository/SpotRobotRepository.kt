@file:Suppress("MagicNumber")

package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import kotlin.uuid.Uuid

/**
 * Repository interface for managing Robot entities.
 */
interface SpotRobotRepository {
    /**
     * Returns the list of all robots.
     */
    fun getRobots(): List<Robot>

    /**
     * Returns the robot with the given id, or null if not found.
     *
     * @param id the unique identifier of the robot
     */
    fun getRobotById(id: Uuid): Robot?

    /**
     * Updates the position of the robot with the given id.
     *
     * @param id the unique identifier of the robot
     * @param position the new position to set
     * @return true if the update was successful, false otherwise
     */
    fun updateRobotPosition(id: Uuid, position: RobotPosition): Boolean

    /**
     * Updates the state of the robot with the given id.
     *
     * @param id the unique identifier of the robot
     * @param state the new state to set
     * @return true if the update was successful, false otherwise
     */
    fun updateRobotState(id: Uuid, state: RobotState): Boolean
}

/**
 * Fake implementation of SpotRobotRepository for testing purposes.
 * Stores robots in memory.
 */
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
