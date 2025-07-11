package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Marker.SpotMarker
import kotlin.uuid.Uuid

/**
 * Repository interface for managing SpotMarker entities.
 */
interface SpotMarkerRepository : MarkerRepository<SpotMarker>

/**
 * Fake implementation of SpotMarkerRepository for testing purposes.
 * Stores markers in memory.
 */
class FakeSpotMarkerRepository : SpotMarkerRepository {
    private val markers = mutableListOf<SpotMarker>(
        SpotMarker(Uuid.random(), "Waypoint 1"),
        SpotMarker(Uuid.random(), "Waypoint 2"),
        SpotMarker(Uuid.random(), "Waypoint 3"),
    )

    /**
     * Returns the list of all spot markers.
     */
    override fun getMarkers(): List<SpotMarker> = markers

    /**
     * Returns the spot marker with the given id, or null if not found.
     *
     * @param id the unique identifier of the marker
     */
    override fun getMarkerById(id: Uuid): SpotMarker? = markers.find { it.id == id }

    /**
     * Adds a new spot marker to the repository.
     *
     * @param spotMarker the marker to add
     * @throws IllegalArgumentException if a marker with the same id already exists
     */
    override fun createMarker(spotMarker: SpotMarker) {
        requireNotNull(getMarkerById(spotMarker.id)) { "Marker with id ${spotMarker.id} already exists." }
        markers.add(spotMarker)
    }

    /**
     * Deletes the spot marker with the given id.
     *
     * @param id the unique identifier of the marker to delete
     * @return true if the marker was removed, false otherwise
     */
    override fun deleteMarker(id: Uuid): Boolean = markers.removeIf { it.id == id }
}
