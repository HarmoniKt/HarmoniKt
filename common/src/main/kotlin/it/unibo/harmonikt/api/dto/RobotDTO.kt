package it.unibo.harmonikt.api.dto

import io.ktor.server.plugins.requestvalidation.ValidationResult
import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.model.RobotType
import kotlinx.serialization.SerialName
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

/**
 * Data Transfer Object (DTO) for robot registration.
 *
 * This interface defines the structure for registering different types of robots
 * in the system, such as MIR and SPOT robots. Each robot type has its own specific
 * registration details.
 */
@Serializable
sealed interface RobotRegistrationDTO {
    /**
     * The canonical name of the robot.
     */
    val canonicalName: CanonicalName

    /**
     * Data Transfer Object (DTO) for registering a MIR robot.
     *
     * @property type The type of the robot, defaulting to "MIR".
     * @property canonicalName The canonical name of the robot.
     * @property apiToken The API token for authentication.
     * @property host The host address of the robot.
     */
    @Serializable
    @SerialName("MIR")
    data class MirRobotRegistrationDTO(
        override val canonicalName: CanonicalName,
        val apiToken: String,
        val host: String,
    ) : RobotRegistrationDTO

    /**
     * Data Transfer Object (DTO) for registering a SPOT robot.
     *
     * @property canonicalName The canonical name of the robot.
     * @property host The host address of the robot.
     * @property username The username for authentication.
     * @property password The password for authentication.
     */
    @Serializable
    @SerialName("SPOT")
    data class SpotRobotRegistrationDTO(
        override val canonicalName: CanonicalName,
        val host: String,
        val username: String,
        val password: String,
    ) : RobotRegistrationDTO

    /**
     * Companion object for validating robot registration DTOs.
     *
     * This object provides a method to validate the fields of the robot registration DTOs,
     * ensuring that all required fields are properly filled out before registration.
     */
    companion object {
        /**
         * Validates the provided robot registration DTO.
         */
        fun validate(dto: RobotRegistrationDTO): ValidationResult = when (dto) {
            is MirRobotRegistrationDTO -> {
                when {
                    dto.canonicalName.isBlank() -> ValidationResult.Invalid("Canonical name must not be empty")
                    dto.apiToken.isBlank() -> ValidationResult.Invalid("API token must not be empty")
                    dto.host.isBlank() -> ValidationResult.Invalid("Host must not be empty")
                    else -> ValidationResult.Valid
                }
            }
            is SpotRobotRegistrationDTO -> {
                when {
                    dto.canonicalName.isBlank() -> ValidationResult.Invalid("Canonical name must not be empty")
                    dto.host.isBlank() -> ValidationResult.Invalid("Host must not be empty")
                    dto.username.isBlank() -> ValidationResult.Invalid("Username must not be empty")
                    dto.password.isBlank() -> ValidationResult.Invalid("Password must not be empty")
                    else -> ValidationResult.Valid
                }
            }
        }
    }
}

/**
 * Data Transfer Object (DTO) representing the response after a robot is created.
 *
 * This class encapsulates the unique identifier of the newly created robot,
 * allowing clients to reference or interact with the robot in subsequent operations.
 *
 * @property id The unique identifier of the created robot, represented as a `RobotId`.
 */
@Serializable
data class RobotIdDTO(val id: RobotId) {
    /**
     * Converts this DTO to a domain model RobotId.
     */
    fun toRobotId(): RobotId = id
}
