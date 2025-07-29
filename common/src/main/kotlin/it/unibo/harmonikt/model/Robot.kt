package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Type alias for representing a unique robot identifier.
 * This alias is used across the system to uniquely identify individual robots.
 */
typealias RobotId = Uuid

/**
 * Represents the type of robot within the system.
 * Determines the category or model of the robot and guides its specific functionalities,
 * such as marker type interpretation or action compatibility.
 *
 * @property MIR Refers to MIR robots, typically used for tasks
 *  requiring precise navigation or environmental interaction.
 * @property SPOT Refers to Spot robots, often used for agile,
 *  dynamic operations or tasks requiring mobility in complex terrains.
 */
enum class RobotType {
    /**
     * Represents the MIR robot type.
     * MIR robots are designed for tasks requiring precise navigation or interaction with the environment.
     *
     * They are associated with MIR-specific markers and typically utilize `MirMarker` to identify
     * locations or points of interest in a given environment.
     */
    MIR,

    /**
     * Represents the Spot robot type.
     * Spot robots are designed to operate in dynamic environments requiring high mobility and agility.
     * They typically utilize `SpotMarker` for recognizing points of interest or waypoints in their environment.
     * This type is used to categorize robots for their specific capabilities and actions within the system.
     */
    SPOT,
}

/**
 * Represents a robot in the system.
 * This class is used at the `robot service` level to model robot entities.
 */
@Serializable
data class Robot(
    /**
     * Unique identifier for the robot.
     */
    val id: RobotId,

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

    /**
     * The type of robot, indicating its model or category (e.g., MIR, SPOT).
     */
    val type: RobotType,
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
    val x: Double,

    /**
     * The y-coordinate of the robot's position.
     */
    val y: Double,
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
