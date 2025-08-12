package it.unibo.harmonikt.api.dto

import io.ktor.server.plugins.requestvalidation.ValidationResult
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.PointOfInterest
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Sealed interface representing a point of interest registration DTO.
 * This interface is used to register points of interest in the system.
 * It includes properties for the unique identifier, canonical name,
 * latitude, and longitude of the point of interest.
 */
@Serializable
data class PointOfInterestRegistrationDTO(

    /**
     * Canonical name of the point of interest.
     * This name is used to uniquely identify the point of interest in the system.
     */
    val name: String,

    /**
     * Latitude coordinate of the point of interest.
     * This value represents the geographical latitude of the point of interest.
     */
    val latitude: Double,

    /**
     * Longitude coordinate of the point of interest.
     * This value represents the geographical longitude of the point of interest.
     */
    val longitude: Double,
)

/**
 * Data Transfer Object (DTO) for representing a point of interest in the environment.
 * This DTO encapsulates the necessary information about a point of interest,
 * including its unique identifier, name, geographical coordinates,
 * and associated markers.
 * This DTO is used to transfer data between different layers of the application,
 * such as from the API to the domain model.
 * * @property id Unique identifier for the point of interest.
 * * @property name Canonical name of the point of interest.
 * * @property latitude Latitude coordinate of the point of interest.
 * * @property longitude Longitude coordinate of the point of interest.
 * * @property associatedMarkers List of markers associated with this point of interest.
 * * Different robot types may use different marker types to identify the same location.
 */
@Serializable
data class PointOfInterestDTO(
    val id: Uuid,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val associatedMarkers: List<Marker>,
) {
    /**
     * Converts this DTO to a domain model PointOfInterest.
     *
     * @return A PointOfInterest domain model representing this DTO.
     */
    fun toPointOfInterest(): PointOfInterest = PointOfInterest(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        associatedMarkers = associatedMarkers,
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
            id = pointOfInterest.id,
            name = pointOfInterest.name,
            latitude = pointOfInterest.latitude,
            longitude = pointOfInterest.longitude,
            associatedMarkers = pointOfInterest.associatedMarkers,
        )
    }
}

/**
 * Data Transfer Object (DTO) representing the unique identifier of a point of interest.
 * This DTO is used to transfer the ID of a point of interest
 * between different layers of the application,
 * such as from the API to the domain model.
 *
 * @property id The unique identifier of the point of interest, represented as a Uuid.
 *
 * This DTO is typically used in scenarios where only the ID of the point of interest is needed,
 * such as when referencing a point of interest in other operations
 * or when performing lookups.
 */
@Serializable
data class PointOfInterestIdDTO(val id: Uuid) {
    /**
     * Converts this DTO to a domain model PointOfInterest.Id.
     *
     * @return The corresponding PointOfInterest.Id domain model.
     */
    fun toPointOfInterestId(): Uuid = id
}
