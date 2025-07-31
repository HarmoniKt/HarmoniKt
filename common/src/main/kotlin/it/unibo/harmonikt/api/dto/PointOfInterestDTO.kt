package it.unibo.harmonikt.api.dto

import io.ktor.server.plugins.requestvalidation.ValidationResult
import it.unibo.harmonikt.model.PointOfInterest
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing basic information about a point of interest in the system.
 *
 * This class encapsulates the essential attributes of a point of interest, including its associated markers.
 * It is used for transmitting point of interest-related data between components of the system in a structured manner.
 *
 * @property associatedMarkers The list of markers associated with this point of interest.
 *                            Different robot types may use different marker types to identify the same location.
 */
@Serializable
data class PointOfInterestDTO(val associatedMarkers: List<MarkerDTO>) {
    /**
     * Converts this DTO to a domain model PointOfInterest.
     *
     * @return A PointOfInterest domain model representing this DTO.
     */
    fun toPointOfInterest(): PointOfInterest = PointOfInterest(
        associatedMarkers = associatedMarkers.map { it.toMarker() },
    )

    /**
     * Companion object for validating point of interest DTOs and converting between DTOs and domain models.
     *
     * This object provides methods to validate the fields of the point of interest DTOs,
     * ensuring that all required fields are properly filled out, and to convert
     * between PointOfInterestDTO and PointOfInterest domain models.
     */
    companion object {
        /**
         * Validates the provided point of interest DTO.
         *
         * @param dto The PointOfInterestDTO to validate.
         * @return A ValidationResult indicating whether the DTO is valid.
         */
        fun validate(dto: PointOfInterestDTO): ValidationResult = when {
            dto.associatedMarkers.isEmpty() -> ValidationResult.Invalid(
                "Point of interest must have at least one associated marker",
            )
            else -> ValidationResult.Valid
        }

        /**
         * Creates a PointOfInterestDTO from a PointOfInterest domain model.
         *
         * @param pointOfInterest The PointOfInterest domain model to convert.
         * @return A PointOfInterestDTO representing the given PointOfInterest.
         */
        fun fromPointOfInterest(pointOfInterest: PointOfInterest): PointOfInterestDTO = PointOfInterestDTO(
            associatedMarkers = pointOfInterest.associatedMarkers.map { MarkerDTO.fromMarker(it) },
        )
    }
}
