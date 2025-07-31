package it.unibo.harmonikt.api.dto

import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Data Transfer Object (DTO) for markers in the environment.
 *
 * This sealed interface defines the structure for different types of markers
 * in the system, such as MIR and SPOT markers. Each marker type has its own specific
 * details.
 */
@Serializable
sealed interface MarkerDTO {
    val id: Uuid

    /**
     * Data Transfer Object (DTO) for a MIR marker.
     *
     * @property id The unique identifier of the marker.
     * @property identifier The string identifier used by MIR robots to recognize this marker.
     */
    @Serializable
    @SerialName("MIR")
    data class MirMarkerDTO(
        override val id: Uuid,
        val identifier: String,
    ) : MarkerDTO

    /**
     * Data Transfer Object (DTO) for a SPOT marker.
     *
     * @property id The unique identifier of the marker.
     * @property waypoint The waypoint string used by Spot robots to recognize this marker.
     */
    @Serializable
    @SerialName("SPOT")
    data class SpotMarkerDTO(
        override val id: Uuid,
        val waypoint: String,
    ) : MarkerDTO

    /**
     * Companion object for validating marker DTOs.
     *
     * This object provides a method to validate the fields of the marker DTOs,
     * ensuring that all required fields are properly filled out.
     */
    companion object {
        /**
         * Validates the provided marker DTO.
         */
        fun validate(dto: MarkerDTO): ValidationResult = when (dto) {
            is MirMarkerDTO -> {
                when {
                    dto.id.toString().isBlank() -> ValidationResult.Invalid("ID must not be empty")
                    dto.identifier.isBlank() -> ValidationResult.Invalid("Identifier must not be empty")
                    else -> ValidationResult.Valid
                }
            }
            is SpotMarkerDTO -> {
                when {
                    dto.id.toString().isBlank() -> ValidationResult.Invalid("ID must not be empty")
                    dto.waypoint.isBlank() -> ValidationResult.Invalid("Waypoint must not be empty")
                    else -> ValidationResult.Valid
                }
            }
        }
    }
}

/**
 * Data Transfer Object (DTO) representing the response after a marker is created.
 *
 * This class encapsulates the unique identifier of the newly created marker,
 * allowing clients to reference or interact with the marker in subsequent operations.
 *
 * @property id The unique identifier of the created marker.
 */
@Serializable
data class MarkerIdDTO(val id: Uuid) {
    fun toMarkerId(): Uuid = id
}
