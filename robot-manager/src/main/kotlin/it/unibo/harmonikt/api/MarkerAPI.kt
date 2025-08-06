package it.unibo.harmonikt.api

import arrow.core.Either
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.resources.PointOfInterests
import it.unibo.harmonikt.resources.PointOfInterests.Id.Markers
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Sealed interface representing errors that can occur in the Marker Management API.
 * This interface defines various error types that can be returned by the API methods.
 */
@Serializable
sealed interface MarkerAPIError {
    /**
     * Represents an error that occurs when a marker is not found.
     * This error is thrown when the requested marker does not exist in the system.
     * @param id The unique identifier of the marker that was not found.
     */
    @Serializable
    data class MarkerNotFound(val id: Uuid) : MarkerAPIError {
        override fun toString(): String = "Marker with ID $id not found"
    }

    /**
     * Represents an error that occurs when a marker already exists.
     * This error is thrown when attempting to create a marker that already exists in the system.
     * @param id The unique identifier of the marker that already exists.
     */
    @Serializable
    data class MarkerAlreadyExists(val id: Uuid) : MarkerAPIError {
        override fun toString(): String = "Marker with ID $id already exists"
    }

    /**
     * Represents a generic error in the Marker Management API.
     * This error is thrown for any other issues that do not fall under specific categories.
     * It may include issues such as network errors, server errors, or unexpected conditions.
     * @param message An optional message providing additional details about the error.
     */
    @Serializable
    data class GenericMarkerAPIError(val message: String? = null) : MarkerAPIError {
        override fun toString(): String = message ?: "An unknown error occurred in the Marker Management API"
    }

    /**
     * Represents an error that occurs when a marker cannot be associated with a point of interest.
     * This error is thrown when the association of a marker to a point of interest fails for any reason.
     * @param markerId The unique identifier of the marker that failed to associate.
     * @param poiId The unique identifier of the point of interest that the marker was supposed to be associated with.
     */
    @Serializable
    data class MarkerAssociationFailed(val markerId: Uuid, val poiId: Uuid) : MarkerAPIError {
        override fun toString(): String =
            "Failed to associate marker with ID $markerId to Point of Interest with ID $poiId"
    }

    /**
     * Represents an error that occurs when a marker cannot be deleted.
     * This error is thrown when the deletion of a marker fails for any reason.
     * @param id The unique identifier of the marker that failed to delete.
     */
    @Serializable
    data class MarkerDeletionFailed(val id: Uuid) : MarkerAPIError {
        override fun toString(): String = "Failed to delete marker with ID $id"
    }
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
