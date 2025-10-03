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
    batteryLevel = BatteryLevel(batteryPercentage),
    state = when (stateText) {
        "Ready" -> RobotState.IDLE
        "Executing" -> RobotState.ON_MISSION
        "Recharging" -> RobotState.RECHARGING
        else -> RobotState.IDLE
    },
)

/**
 * Represents the detailed status of a MiR robot as provided by its API.
 * This data transfer object encapsulates various attributes related to the robot's current state and capabilities.
 * @property allowedMethods A string representing the methods allowed for the robot.
 * @property batteryPercentage The current battery percentage of the robot.
 * @property batteryTimeRemaining The estimated time remaining for the battery in seconds.
 * @property distanceToNextTarget The distance to the next target in meters.
 * @property errors A list of error messages currently affecting the robot.
 * @property footprint The footprint of the robot, typically representing its physical dimensions.
 * @property joystickLowSpeedModeEnabled A boolean indicating if the low-speed mode for the joystick is enabled.
 * @property joystickWebSessionId The session ID for the joystick web interface.
 * @property mapId The identifier of the current map the robot is operating on.
 * @property missionQueueId The identifier of the current mission queue, if any.
 * @property missionQueueUrl The URL of the current mission queue, if any.
 * @property missionText A textual description of the current mission.
 * @property modeId The identifier of the current mode the robot is in.
 * @property modeKeyState The key state of the current mode.
 * @property modeText A textual description of the current mode.
 * @property moved The distance the robot has moved in meters.
 * @property position The current position of the robot, including orientation and coordinates.
 * @property robotModel The model of the robot.
 * @property robotName The name of the robot.
 * @property safetySystemMuted A boolean indicating if the safety system is muted.
 * @property serialNumber The serial number of the robot.
 * @property sessionId The current session ID for the robot.
 * @property stateId The identifier of the current state the robot is in.
 * @property stateText A textual description of the current state.
 * @property unloadedMapChanges A boolean indicating if there are unloaded map changes.
 * @property uptime The uptime of the robot in seconds.
 * @property userPrompt A prompt message for the user, if any.
 * @property velocity The current velocity of the robot, including linear and angular components.
 */
@Serializable
data class MirStatusDTO(
    val allowedMethods: String?, // can refine if you know type
    val batteryPercentage: Double,
    val batteryTimeRemaining: Long,
    val distanceToNextTarget: Double,
    val errors: List<String?>,
    val footprint: String,
    val joystickLowSpeedModeEnabled: Boolean,
    val joystickWebSessionId: String,
    val mapId: String,
    val missionQueueId: String?,
    val missionQueueUrl: String?,
    val missionText: String,
    val modeId: Int,
    val modeKeyState: String,
    val modeText: String,
    val moved: Double,
    val position: Position,
    val robotModel: String,
    val robotName: String,
    val safetySystemMuted: Boolean,
    val serialNumber: String,
    val sessionId: String,
    val stateId: Int,
    val stateText: String,
    val unloadedMapChanges: Boolean,
    val uptime: Int,
    val userPrompt: String?,
    val velocity: Velocity,
)

/**
 * Represents the position of a robot, including its orientation and coordinates.
 * @property orientation The orientation of the robot in degrees.
 * @property x The X coordinate of the robot.
 * @property y The Y coordinate of the robot.
 */
@Serializable
data class Position(val orientation: Double, val x: Double, val y: Double)

/**
 * Represents the velocity of a robot, including its angular and linear components.
 * @property angular The angular velocity of the robot in degrees per second.
 * @property linear The linear velocity of the robot in meters per second.
 */
@Serializable
data class Velocity(val angular: Double, val linear: Double)
