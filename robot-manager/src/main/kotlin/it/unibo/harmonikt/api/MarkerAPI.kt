package it.unibo.harmonikt.api

import arrow.core.Either
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.resources.Markers
import it.unibo.harmonikt.resources.PointOfInterests
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
sealed interface MarkerAPIError {
    /**
     * Represents an error that occurs when a marker is not found.
     * This error is thrown when the requested marker does not exist in the system.
     */
    @Serializable
    data class MarkerNotFound(val id: Uuid) : MarkerAPIError

    /**
     * Represents an error that occurs when a marker already exists.
     * This error is thrown when attempting to create a marker that already exists in the system.
     */
    @Serializable
    data class MarkerAlreadyExists(val id: Uuid) : MarkerAPIError

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
    data class MarkerDeletionFailed(val id: Uuid) : MarkerAPIError
}

/**
 * Interface defining the Marker Management API.
 * This interface provides methods for managing markers in the system.
 */
interface MarkerAPI {
    /**
     * Retrieves all markers in the system.
     *
     * @return A list of all markers.
     */
    suspend fun getAllMarkers(): Either<MarkerAPIError, List<Marker>>

    /**
     * Retrieves a marker by its unique identifier.
     *
     * @param markerId The unique identifier of the marker to retrieve.
     * @return The marker associated with the given identifier.
     */
    suspend fun getMarkerInfo(poiId: PointOfInterests.Id, markerId: Markers.Id): Either<MarkerAPIError, MarkerIdDTO>
}
