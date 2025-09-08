package it.unibo.harmonikt

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotState
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
 * Data Transfer Object for Mir robot status.
 * @property position The position of the robot.
 * @property batteryPercentage The battery percentage.
 * @property stateId The state identifier.
 * @property stateText The textual description of the state.
 */
@Serializable
data class MirStatus(val position: MirPosition, val state: RobotState, val batteryPercentage: BatteryLevel)

/**
 * Represents the position of a Mir robot.
 * @property x The X coordinate.
 * @property y The Y coordinate.
 * @property orientation The orientation angle.
 */
@Serializable
data class MirPosition(val x: Double, val y: Double, val orientation: Double)

/**
 * Data Transfer Object for Mir robot status.
 * @property position The position of the robot.
 * @property batteryPercentage The battery percentage.
 * @property stateId The state identifier.
 * @property stateText The textual description of the state.
 */
@Serializable
data class MirStatusDTO(
    val position: MirPosition,
    val batteryPercentage: Double,
    val stateId: Int,
    val stateText: String,
)

/**
 * Converts a MirStatusDTO to a MirStatus domain object.
 * @return The corresponding MirStatus domain object.
 */
fun MirStatusDTO.toDomain(): MirStatus = MirStatus(
    position = MirPosition(position.x, position.y, position.orientation),
    batteryPercentage = BatteryLevel(batteryPercentage),
    state = when (stateId) {
        1 -> RobotState.IDLE
        3 -> RobotState.ON_MISSION
        4 -> RobotState.RECHARGING
        else -> RobotState.IDLE // fallback, puoi fare anche null o un DEFAULT
    },
)
