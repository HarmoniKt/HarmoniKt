package it.unibo.harmonikt

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotState
import kotlinx.serialization.Serializable

/**
 * Represents a robot in the system.
 * This class is used at the `robot service` level to model robot entities.
 */
@Serializable
data class MirRobot(val id: RobotId, val name: CanonicalName, val apiToken: String, val host: String)

@Serializable
data class MirStatus(val position: MirPosition, val state: RobotState, val batteryPercentage: BatteryLevel)

@Serializable
data class MirPosition(val x: Double, val y: Double, val orientation: Double)

@Serializable
data class MirStatusDTO(
    val position: MirPosition,
    val batteryPercentage: Double,
    val stateId: Int,
    val stateText: String,
)

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
