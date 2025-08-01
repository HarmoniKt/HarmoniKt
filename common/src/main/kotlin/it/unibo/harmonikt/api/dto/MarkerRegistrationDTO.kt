package it.unibo.harmonikt.api.dto

import io.ktor.server.plugins.requestvalidation.ValidationResult
import it.unibo.harmonikt.model.Marker
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
sealed interface MarkerRegistrationDTO {
    /**
     * Unique identifier for the marker.
     *
     * This identifier is used to uniquely reference a marker in the system.
     */
    val id: Uuid

    /**
     * Data Transfer Object (DTO) for a MIR marker.
     *
     * @property id The unique identifier of the marker.
     * @property identifier The string identifier used by MIR robots to recognize this marker.
     */
    @Serializable
    @SerialName("MIR")
    data class MirMarkerRegistrationDTO(override val id: Uuid, val identifier: String) : MarkerRegistrationDTO

    /**
     * Data Transfer Object (DTO) for a SPOT marker.
     *
     * @property id The unique identifier of the marker.
     * @property fiducial The waypoint string used by Spot robots to recognize this marker.
     */
    @Serializable
    @SerialName("SPOT")
    data class SpotMarkerRegistrationDTO(override val id: Uuid, val fiducial: Int) : MarkerRegistrationDTO

    /**
     * Converts this DTO to a Marker model object.
     *
     * @return The corresponding Marker model object.
     */
    fun toMarker(): Marker = when (this) {
        is MirMarkerRegistrationDTO -> Marker.MirMarker(
            id = id,
            identifier = identifier,
        )
        is SpotMarkerRegistrationDTO -> Marker.SpotMarker(
            id = id,
            fiducial = fiducial,
        )
    }

    /**
     * Companion object for validating marker DTOs and converting between DTOs and domain models.
     *
     * This object provides methods to validate the fields of the marker DTOs,
     * ensuring that all required fields are properly filled out, and to convert
     * between MarkerDTO and Marker domain models.
     */

    companion object {
        /**
         * Validates the provided marker DTO.
         */
        fun validate(dto: MarkerRegistrationDTO): ValidationResult = when (dto) {
            is MirMarkerRegistrationDTO -> {
                when {
                    dto.id.toString().isBlank() -> ValidationResult.Invalid("Mir Marker ID must not be empty")
                    dto.identifier.isBlank() -> ValidationResult.Invalid("Mir Marker Identifier must not be empty")
                    else -> ValidationResult.Valid
                }
            }
            is SpotMarkerRegistrationDTO -> {
                when {
                    dto.id.toString().isBlank() -> ValidationResult.Invalid("Spot Marker ID must not be empty")
                    dto.fiducial.toString().isBlank() -> ValidationResult.Invalid(
                        "Spot Marker Fiducial must not be empty",
                    )
                    else -> ValidationResult.Valid
                }
            }
        }

        /**
         * Creates a MarkerDTO from a Marker model object.
         *
         * @param marker The Marker model object to convert.
         * @return The corresponding MarkerDTO.
         */
        fun fromMarker(marker: Marker): MarkerRegistrationDTO = when (marker) {
            is Marker.MirMarker -> MirMarkerRegistrationDTO(
                id = marker.id,
                identifier = marker.identifier,
            )
            is Marker.SpotMarker -> SpotMarkerRegistrationDTO(
                id = marker.id,
                fiducial = marker.fiducial,
            )
        }
    }
}

/**
 * Data Transfer Object (DTO) representing the response after a marker is created.
 *
 * This class encapsulates the unique identifier of the newly created marker,
 * allowing clients to reference or interact with the marker in later operations.
 *
 * @property id The unique identifier of the created marker.
 */
@Serializable
data class MarkerIdDTO(val id: Uuid) {
    /**
     * Converts this DTO to a MarkerId.
     */
    fun toMarkerId(): Uuid = id
}
