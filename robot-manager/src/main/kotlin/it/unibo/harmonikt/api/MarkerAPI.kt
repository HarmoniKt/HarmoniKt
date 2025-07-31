package it.unibo.harmonikt.api

import arrow.core.Either
import it.unibo.harmonikt.model.Marker
import kotlinx.serialization.Serializable

@Serializable
sealed interface MarkerAPIError {
    /**
     * Represents an error that occurs when a marker is not found.
     * This error is thrown when the requested marker does not exist in the system.
     */
    @Serializable
    data class MarkerNotFound(val message: String) : MarkerAPIError

    /**
     * Represents an error that occurs when a marker already exists.
     * This error is thrown when attempting to create a marker that already exists in the system.
     */
    @Serializable
    data class MarkerAlreadyExists(val message: String) : MarkerAPIError

    /**
     * Represents a generic error in the Marker Management API.
     * This error is thrown for any other issues that do not fall under specific categories.
     */
    @Serializable
    data class GenericMarkerAPIError(val message: String? = null) : MarkerAPIError

    /**
     * Represents an error that occurs when a marker cannot be deleted.
     * This error is thrown when the deletion of a marker fails for any reason.
     */
    @Serializable
    data class MarkerDeletionFailed(val message: String) : MarkerAPIError
}

/**
 * Interface defining the Marker Management API.
 * This interface provides methods for managing markers in the system.
 */
interface MarkerAPI {
    /**
     * Retrieves a marker by its unique identifier.
     *
     * @param id The unique identifier of the marker to retrieve.
     * @return The marker associated with the given identifier.
     */
    suspend fun getMarker(id: String): Either<MarkerAPIError, Marker>

    /**
     * Creates a new marker in the system.
     *
     * @param marker The marker to create.
     * @return The created marker.
     */
    suspend fun registerMarker(marker: Marker): Either<MarkerAPIError, Marker>

    /**
     * Deletes a marker by its unique identifier.
     *
     * @param id The unique identifier of the marker to delete.
     */
    suspend fun deleteMarker(id: String): Either<MarkerAPIError, Marker>

    /**
     * Retrieves all markers in the system.
     *
     * @return A list of all markers.
     */
    suspend fun getAllMarkers(): Either<MarkerAPIError, List<Marker>>
}
