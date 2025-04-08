package it.unibo.harmonikt

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Operate at `robot service` level.
 */
@OptIn(ExperimentalUuidApi::class)
data class Robot(
    val id: Uuid,
    val name: CanonicalName,
    val batteryLevel: BatteryLevel,
    val currentPosition: RobotPosition,
    val currentState: RobotState,
)

/**
 * The level of the battery.
 */
@JvmInline
value class BatteryLevel(val value: Double) {
    init {
        require(value in 0.0..100.0) { "Battery level must be between 0 and 100.0" }
    }
}

/**
 * The name related to the robot.
 */
@JvmInline
value class CanonicalName(val name: String)

/**
 * The position of the robot, supposed to be in a 2D environment.
 */
data class RobotPosition(val x: Int, val y: Int)

/**
 * What the robot is physically doing.
 */
enum class RobotState {
    /**
     * Waiting for inputs.
     */
    IDLE,

    /**
     * Robot is doing something.
     */
    ON_MISSION,

    /**
     * Robot is at recharging station.
     */
    RECHARGING,
}
