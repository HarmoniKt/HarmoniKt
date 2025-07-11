package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Marker
import kotlin.uuid.Uuid

/**
 * Repository interface for managing markers in the system.
 * This generic interface defines the contract for storing, retrieving, and managing
 * markers that can be recognized by robots.
 *
 * @param M The specific type of marker this repository manages, must be a subtype of [Marker].
 */
interface MarkerRepository<M : Marker> {
    /**
     * Retrieves all markers stored in the repository.
     *
     * @return A list of all markers of type [M].
     */
    fun getMarkers(): List<M>

    /**
     * Retrieves a specific marker by its unique identifier.
     *
     * @param id The unique identifier of the marker to retrieve.
     * @return The marker with the specified ID, or null if no such marker exists.
     */
    fun getMarkerById(id: Uuid): M?

    /**
     * Creates a new marker in the repository.
     *
     * @param marker The marker to create.
     */
    fun createMarker(marker: M)

    /**
     * Deletes a marker from the repository.
     *
     * @param id The unique identifier of the marker to delete.
     * @return true if the marker was successfully deleted, false otherwise.
     */
    fun deleteMarker(id: Uuid): Boolean
}
