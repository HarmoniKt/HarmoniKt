package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.repository.RobotRepository
import kotlin.uuid.Uuid

/**
 * Implementation of the RobotRepository interface for the Robot Manager service.
 * This class provides access to robot data in the Robot Manager service.
 */
class RobotRepositoryRobotManager : RobotRepository {
    // In-memory storage for robots
    private val robots = mutableMapOf<RobotId, Robot>()

    // Initialize with some sample robots
    init {
        // Sample MIR robot
        val mirRobot = Robot(
            id = Uuid.random(),
            name = CanonicalName("MIR-100"),
            batteryLevel = BatteryLevel(85.5),
            currentPosition = RobotPosition(10, 20),
            currentState = RobotState.IDLE,
            type = RobotType.MIR,
        )
        robots[mirRobot.id] = mirRobot

        // Sample SPOT robot
        val spotRobot = Robot(
            id = Uuid.random(),
            name = CanonicalName("SPOT-1"),
            batteryLevel = BatteryLevel(92.0),
            currentPosition = RobotPosition(30, 40),
            currentState = RobotState.ON_MISSION,
            type = RobotType.SPOT,
        )
        robots[spotRobot.id] = spotRobot
    }

    /**
     * Returns the list of all robots.
     */
    override fun getRobots(): List<RobotId> = robots.keys.toList()


    /**
     * Returns the robot with the given id, or null if not found.
     *
     * @param id the unique identifier of the robot
     */
    override fun getRobotById(id: RobotId): Robot? {
        return robots[id]
    }

    /**
     * Updates the position of the robot with the given id.
     *
     * @param id the unique identifier of the robot
     * @param position the new position to set
     * @return true if the update was successful, false otherwise
     */
    override fun updateRobotPosition(id: RobotId, position: RobotPosition): Boolean {
        val robot = robots[id] ?: return false
        robots[id] = robot.copy(currentPosition = position)
        return true
    }

    /**
     * Updates the state of the robot with the given id.
     *
     * @param id the unique identifier of the robot
     * @param state the new state to set
     * @return true if the update was successful, false otherwise
     */
    override fun updateRobotState(id: RobotId, state: RobotState): Boolean {
        val robot = robots[id] ?: return false
        robots[id] = robot.copy(currentState = state)
        return true
    }

    /**
     * Adds a new robot to the repository.
     *
     * @param robot the robot to add
     * @return true if the robot was added successfully, false otherwise
     */
    fun addRobot(robot: Robot) {
        robots[robot.id] = robot
    }

    /**
     * Removes a robot from the repository.
     *
     * @param id the unique identifier of the robot to remove
     * @return true if the robot was removed successfully, false otherwise
     */
    fun removeRobot(id: RobotId): Boolean {
        return robots.remove(id) != null
    }
}
