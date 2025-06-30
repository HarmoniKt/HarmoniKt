package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Represents a robot in the system.
 * This class is used at the `robot service` level to model robot entities.
 */
@Serializable
data class Robot(
    /**
     * Unique identifier for the robot.
     */
    val id: Uuid,

    /**
     * Canonical name of the robot.
     */
    val name: CanonicalName,

    /**
     * Current battery level of the robot.
     */
    val batteryLevel: BatteryLevel,

    /**
     * Current position of the robot in the environment.
     */
    val currentPosition: RobotPosition,

    /**
     * Current operational state of the robot.
     */
    val currentState: RobotState,
)

/**
 * Represents the battery level of a robot.
 * The value is constrained to be between 0.0 and 100.0 percent.
 */
@JvmInline
@Serializable
value class BatteryLevel(
    /**
     * The battery level percentage value (0.0-100.0).
     */
    val value: Double,
) {
    init {
        require(value in 0.0..100.0) { "Battery level must be between 0 and 100.0" }
    }
}

/**
 * Represents a standardized name format for robots in the system.
 * This value class encapsulates the robot's name to ensure consistent naming conventions.
 */
@JvmInline
@Serializable
value class CanonicalName(
    /**
     * The string representation of the robot's name.
     */
    val name: String,
)

/**
 * Represents the position of a robot in a 2D environment.
 * Uses a Cartesian coordinate system with x and y coordinates.
 */
@Serializable
data class RobotPosition(
    /**
     * The x-coordinate of the robot's position.
     */
    val x: Int,

    /**
     * The y-coordinate of the robot's position.
     */
    val y: Int,
)

/**
 * Represents the current operational state of a robot.
 * Defines the possible physical activities or conditions a robot can be in.
 */
@Serializable
enum class RobotState {
    /**
     * The robot is inactive and waiting for commands.
     * In this state, the robot is not performing any tasks and is ready to accept new missions.
     */
    IDLE,

    /**
     * The robot is actively executing a mission.
     * This state indicates that the robot is engaged in performing a task or following a path.
     */
    ON_MISSION,

    /**
     * The robot is at a recharging station replenishing its battery.
     * In this state, the robot is not available for new missions until charging is complete.
     */
    RECHARGING,
}
