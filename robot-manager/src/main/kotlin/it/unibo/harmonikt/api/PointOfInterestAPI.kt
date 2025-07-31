package it.unibo.harmonikt.api

import arrow.core.Either
import it.unibo.harmonikt.model.PointOfInterest
import kotlinx.serialization.Serializable

@Serializable
sealed interface PointOfInterestAPIError {
    /**
     * Represents a generic error in the Point of Interest API.
     *
     * @property message An optional message describing the error.
     */
    @Serializable
    data class GenericPointOfInterestAPIError(val message: String? = null) : PointOfInterestAPIError

    /**
     * Represents an error when a point of interest with the specified ID is not found.
     *
     * @property poiId The unique identifier of the point of interest that was not found.
     */
    @Serializable
    data class PointOfInterestNotFound(val poiId: String) : PointOfInterestAPIError

    /**
     * Represents an error when a point of interest with the specified ID already exists.
     *
     * @property poiId The unique identifier of the point of interest that already exists.
     */
    @Serializable
    data class PointOfInterestAlreadyExists(val poiId: String) : PointOfInterestAPIError

    /**
     * Represents an error when the deletion of a point of interest fails.
     *
     * @property poiId The unique identifier of the point of interest that could not be deleted.
     */
    @Serializable
    data class PointOfInterestDeletionFailed(val poiId: String) : PointOfInterestAPIError
}

/**
 * Interface defining the Point of Interest Management API.
 * This interface provides methods for managing points of interest in the system.
 */
interface PointOfInterestAPI {
    /**
     * Retrieves a point of interest by its unique identifier.
     *
     * @param id The unique identifier of the point of interest to retrieve.
     * @return The point of interest associated with the given identifier.
     */
    suspend fun getPointOfInterest(id: String): Either<PointOfInterestAPIError, PointOfInterest>

    /**
     * Creates a new point of interest in the system.
     *
     * @param poi The point of interest to create.
     * @return The created point of interest.
     */
    suspend fun registerPointOfInterest(poi: PointOfInterest): Either<PointOfInterestAPIError, PointOfInterest>

    /**
     * Deletes a point of interest by its unique identifier.
     *
     * @param id The unique identifier of the point of interest to delete.
     * @return A result indicating success or failure of the deletion operation.
     */
    suspend fun deletePointOfInterest(id: String): Either<PointOfInterestAPIError, Unit>

    /**
     * Retrieves all points of interest in the system.
     *
     * @return A list of all points of interest.
     */
    suspend fun getAllPointsOfInterest(): Either<PointOfInterestAPIError, List<PointOfInterest>>
}
