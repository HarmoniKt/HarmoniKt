package it.unibo.harmonikt.api.dto

import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import kotlinx.serialization.Serializable

/**
 * A data transfer object (DTO) representing basic information about a robot in the system.
 *
 * This class encapsulates the essential attributes of a robot, including its unique identifier,
 * canonical name, and type. It is used for transmitting robot-related data between components
 * of the system in a structured manner.
 *
 * @property id A unique identifier for the robot, represented as a `RobotId`.
 * @property canonicalName The canonical name of the robot, ensuring a standardized naming convention.
 * @property type The type of the robot, as defined by the `RobotType` enum, indicating the robot's category or model.
 */
@Serializable
data class RobotInfoDTO(val id: RobotId, val canonicalName: CanonicalName, val type: RobotType)

/**
 * Data Transfer Object representing the status of a robot in the system.
 *
 * This class encapsulates various properties that describe the current state,
 * identity, and characteristics of a specific robot instance.
 *
 * @property id Unique identifier for the robot.
 * @property canonicalName The standardized name of the robot.
 * @property state The current operational state of the robot.
 * @property batteryLevel The current battery level of the robot.
 * @property currentPosition The robot's current position within a 2D Cartesian coordinate system.
 * @property type The type or model of the robot, which helps categorize its capabilities and behavior.
 */
@Serializable
data class RobotStatusDTO(
    val id: RobotId,
    val canonicalName: CanonicalName,
    val state: RobotState,
    val batteryLevel: BatteryLevel,
    val currentPosition: RobotPosition,
    val type: RobotType,
)
