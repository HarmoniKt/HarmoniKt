package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.PointOfInterest
import kotlin.uuid.Uuid

/**
 * Repository interface for managing points of interest in the system.
 * This interface defines the contract for storing, retrieving, and managing
 * points of interest that robots can navigate to.
 *
 * Currently, this is a marker interface with no methods defined.
 * Implementations will provide specific functionality for managing points of interest.
 */
interface PointOfInterestRepository {
    /**
     * Retrieves all points of interest stored in the repository.
     *
     * @return A list of all points of interest.
     */
    fun getPointsOfInterest(): List<PointOfInterest>

    /**
     * Checks if a point of interest with the specified ID exists in the repository.
     *
     * @param id The unique identifier of the point of interest to check.
     * @return true if the point of interest exists, false otherwise.
     */
    fun getPointOfInterestById(id: Uuid): PointOfInterest?

    /**
     * Retrieves a specific point of interest by its unique identifier.
     *
     * @param id The unique identifier of the point of interest to retrieve.
     * @return The point of interest with the specified ID, or null if no such point exists.
     */
    fun registerPointOfInterest(id: Uuid, name: String, latitude: Float, longitude: Float): Boolean

    /**
     * Deletes a point of interest from the repository.
     * This method removes the point of interest with the specified ID from the repository.
     *
     * @param id The unique identifier of the point of interest to delete.
     * @return true if the point of interest was successfully deleted, false otherwise.
     */
    fun deletePointOfInterest(id: Uuid): Boolean

    /**
     * Retrieves a point of interest by its unique identifier.
     *
     * @param id The unique identifier of the point of interest to retrieve.
     * @return The point of interest associated with the given identifier, or null if not found.
     */
    fun associateMarker(poiId: Uuid, marker: Marker): Boolean

    /**
     * Retrieves information about a specific marker associated with a point of interest.
     *
     * @param poiId The unique identifier of the point of interest.
     * @param markerId The unique identifier of the marker to retrieve.
     * @return The marker associated with the given point of interest and marker ID, or null if not found.
     */
    fun getMarkerInfo(poiId: Uuid, markerId: Uuid): Marker?

    /**
     * Dissociates a marker from a point of interest.
     *
     * @param poiId The unique identifier of the point of interest.
     * @param markerId The unique identifier of the marker to dissociate.
     * @return true if the dissociation was successful, false otherwise.
     */
    fun dissociateMarker(poiId: Uuid, markerId: Uuid): Boolean
}
