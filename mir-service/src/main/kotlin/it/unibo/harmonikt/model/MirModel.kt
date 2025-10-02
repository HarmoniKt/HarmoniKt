package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

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
data class MirInfoDTO(
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
fun MirStatusDTO.toDomain(canonicalName: String, id: Uuid): MirInfo = MirInfo(
    id = id,
    canonicalName = canonicalName,
    currentPosition = MirPosition(position.x, position.y),
    batteryLevel = BatteryLevel(battery_percentage),
    state = when (state_text) {
        "Ready" -> RobotState.IDLE
        "Executing" -> RobotState.ON_MISSION
        "Recharging" -> RobotState.RECHARGING
        else -> RobotState.IDLE
    },
)

@Serializable
data class MirStatusDTO(
    val allowed_methods: String?, // can refine if you know type
    val battery_percentage: Double,
    val battery_time_remaining: Long,
    val distance_to_next_target: Double,
    val errors: List<String?>,
    val footprint: String,
    val joystick_low_speed_mode_enabled: Boolean,
    val joystick_web_session_id: String,
    val map_id: String,
    val mission_queue_id: String?,
    val mission_queue_url: String?,
    val mission_text: String,
    val mode_id: Int,
    val mode_key_state: String,
    val mode_text: String,
    val moved: Double,
    val position: Position,
    val robot_model: String,
    val robot_name: String,
    val safety_system_muted: Boolean,
    val serial_number: String,
    val session_id: String,
    val state_id: Int,
    val state_text: String,
    val unloaded_map_changes: Boolean,
    val uptime: Int,
    val user_prompt: String?,
    val velocity: Velocity
)

@Serializable
data class Position(
    val orientation: Double,
    val x: Double,
    val y: Double
)

@Serializable
data class Velocity(
    val angular: Double,
    val linear: Double
)
