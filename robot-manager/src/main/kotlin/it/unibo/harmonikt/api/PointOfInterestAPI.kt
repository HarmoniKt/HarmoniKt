package it.unibo.harmonikt.api

import arrow.core.Either
import it.unibo.harmonikt.api.PointOfInterestAPIError.GenericPointOfInterestAPIError
import it.unibo.harmonikt.api.dto.MarkerDTO
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.api.dto.MarkerRegistrationDTO
import it.unibo.harmonikt.api.dto.PointOfInterestDTO
import it.unibo.harmonikt.api.dto.PointOfInterestIdDTO
import it.unibo.harmonikt.api.dto.PointOfInterestRegistrationDTO
import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.resources.PointOfInterests
import it.unibo.harmonikt.resources.PointOfInterests.Id.Markers
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Sealed interface representing errors that can occur in the Point of Interest API.
 * This interface defines various error types that can be returned by the API methods.
 */
@Serializable
sealed interface PointOfInterestAPIError {
    /**
     * Represents a generic error in the Point of Interest API.
     *
     * @property message An optional message describing the error.
     */
    @Serializable
    data class GenericPointOfInterestAPIError(val message: String? = null) : PointOfInterestAPIError {
        override fun toString(): String = message ?: "An unknown error occurred in the Point of Interest API"
    }

    /**
     * Represents an error when a "Point of Interest" with the specified ID is not found.
     *
     * @property poiId The unique identifier of the "Point of Interest" that was not found.
     */
    @Serializable
    data class PointOfInterestNotFound(val poiId: Uuid) : PointOfInterestAPIError {
        override fun toString(): String = "Point of Interest with ID $poiId not found"
    }

    /**
     * Represents an error when a "Point of Interest" with the specified ID already exists.
     *
     * @property poiId The unique identifier of the "Point of Interest" that already exists.
     */
    @Serializable
    data class PointOfInterestAlreadyExists(val poiId: Uuid) : PointOfInterestAPIError {
        override fun toString(): String = "Point of Interest with ID $poiId already exists"
    }

    /**
     * Represents an error when the deletion of a "Point of Interest" fails.
     *
     * @property poiId The unique identifier of the "Point of Interest" that could not be deleted.
     */
    @Serializable
    data class PointOfInterestDeletionFailed(val poiId: Uuid) : PointOfInterestAPIError {
        override fun toString(): String = "Failed to delete Point of Interest with ID $poiId"
    }
}

/**
 * Interface defining the Point of Interest Management API.
 * This interface provides methods for managing points of interest in the system.s
 */
interface PointOfInterestAPI {
    /**
     * Retrieves all points of interest in the system.
     *
     * @return A list of all points of interest.
     */
    suspend fun getAllPointsOfInterest(): Either<GenericPointOfInterestAPIError, List<PointOfInterest>>

    /**
     * Retrieves a "Point of Interest" by its unique identifier.
     *
     * @param poiId The unique identifier of the "Point of Interest" to retrieve.
     * @return The "Point of Interest" associated with the given identifier.
     */
    suspend fun getPointOfInterest(poiId: PointOfInterests.Id): Either<PointOfInterestAPIError, PointOfInterestDTO>

    /**
     * Creates a new "Point of Interest" in the system.
     *
     * @param poi The "Point of Interest" to create.
     * @return The created "Point of Interest".
     */
    suspend fun registerPointOfInterest(
        poi: PointOfInterestRegistrationDTO,
    ): Either<PointOfInterestAPIError, PointOfInterestIdDTO>

    /**
     * Deletes a "Point of Interest" by its unique identifier.
     *
     * @param poiId The unique identifier of the "Point of Interest" to delete.
     * @return A result indicating success or failure of the deletion operation.
     */
    suspend fun deletePointOfInterest(poiId: PointOfInterests.Id): Either<PointOfInterestAPIError, PointOfInterestDTO>

    /**
     * Retrieves all markers associated with a specific "Point of Interest".
     *
     * @param poiId The unique identifier of the "Point of Interest" for which to retrieve markers.
     * @return A list of marker identifiers associated with the specified "Point of Interest".
     */
    suspend fun getPointOfInterestMarkers(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, List<MarkerIdDTO>>

    /**
     * Registers a new marker for a specific "Point of Interest".
     *
     * @param marker The marker registration details.
     * @return The registered marker information or an error if the registration fails.
     */
    suspend fun getMarkerInfo(poiId: PointOfInterests.Id, markerId: Markers.Id): Either<MarkerAPIError, MarkerDTO>

    /**
     * Registers a new marker for a specific "Point of Interest".
     *
     * @param request The marker registration details.
     * @return The registered marker information or an error if the registration fails.
     */
    suspend fun registerMarker(request: MarkerRegistrationDTO): Either<MarkerAPIError, MarkerDTO>

    /**
     * Removes a marker from a specific "Point of Interest".
     *
     * @param poiId The unique identifier of the "Point of Interest" from which to remove the marker.
     * @param markerId The unique identifier of the marker to remove.
     * @return The ID of the removed marker or an error if the removal fails.
     */
    suspend fun removeMarker(poiId: PointOfInterests.Id, markerId: Markers.Id): Either<MarkerAPIError, MarkerIdDTO>
}
