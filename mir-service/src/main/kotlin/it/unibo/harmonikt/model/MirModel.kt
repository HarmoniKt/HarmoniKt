package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable

/**
 * Represents a robot in the system.
 * This class is used at the `robot service` level to model robot entities.
 * @property id The unique identifier of the robot.
 * @property name The canonical name of the robot.
 * @property apiToken The API token for authenticating with the robot.
 * @property host The host address of the robot.
 */
@Serializable
data class MirRobot(val id: RobotId, val name: CanonicalName, val apiToken: String, val host: String)

/**
 * Represents information about a MiR.
 *
 * @property id A unique identifier for the robot.
 * @property canonicalName The canonical name of the robot, typically used for identification purposes.
 * @property currentPosition The current physical position of the robot, including its coordinates and orientation.
 * @property state The current operational state of the robot, such as idle, on a mission, or recharging.
 * @property batteryLevel The battery level of the robot, represented as a percentage (0.0-100.0).
 */
@Serializable
data class MirInfo(
    val id: RobotId,
    val canonicalName: CanonicalName,
    val currentPosition: MirPosition,
    val state: RobotState,
    val batteryLevel: BatteryLevel,
)

/**
 * Represents the position of a Mir robot.
 * @property x The X coordinate.
 * @property y The Y coordinate.
 */
@Serializable
data class MirPosition(val x: Double, val y: Double)

/**
 * Represents the status of a MIR robot.
 * This data transfer object encapsulates the key details about a MIR robot's current state.
 *
 * @property id The unique identifier of the robot.
 * @property canonicalName The canonical name of the robot, typically used for human-readable identification.
 * @property position The current position of the robot, represented by its coordinates.
 * @property batteryLevel The current battery level of the robot, expressed as a percentage.
 * @property stateId The identifier of the robot's current state, used for mapping to predefined state types.
 * @property stateText The textual description of the robot's current state, providing a more descriptive status.
 */
@Serializable
data class MirStatusDTO(
    val id: RobotId,
    val canonicalName: CanonicalName,
    val position: MirPosition,
    val batteryLevel: BatteryLevel,
    val stateId: Int,
    val stateText: String,
)

/**
 * Converts a MirStatusDTO to a MirStatus domain object.
 * @return The corresponding MirStatus domain object.
 */
fun MirStatusDTO.toDomain(): MirInfo = MirInfo(
    id = id,
    canonicalName = canonicalName,
    currentPosition = MirPosition(position.x, position.y),
    batteryLevel = batteryLevel,
    state = when (stateId) {
        1 -> RobotState.IDLE
        3 -> RobotState.ON_MISSION
        4 -> RobotState.RECHARGING
        else -> RobotState.IDLE
    },
)
