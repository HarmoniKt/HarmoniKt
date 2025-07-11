package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Marker

/**
 * Repository interface for managing MirMarker entities.
 * This interface extends the generic MarkerRepository to handle Mir-specific markers.
 */
interface MirMarkerRepository : MarkerRepository<Marker.MirMarker>

/**
 * Fake implementation of MirMarkerRepository for testing purposes.
 * Stores markers in memory.
 */
class FakeMirMarkerRepository : MirMarkerRepository {
    private val markers = mutableListOf(
        Marker.MirMarker(id = kotlin.uuid.Uuid.random(), identifier = "Waypoint 1"),
        Marker.MirMarker(id = kotlin.uuid.Uuid.random(), identifier = "Waypoint 2"),
        Marker.MirMarker(id = kotlin.uuid.Uuid.random(), identifier = "Waypoint 3"),
    )

    override fun getMarkers(): List<Marker.MirMarker> = markers

    override fun getMarkerById(id: kotlin.uuid.Uuid): Marker.MirMarker? = markers.find { it.id == id }

    override fun createMarker(marker: Marker.MirMarker) {
        requireNotNull(getMarkerById(marker.id)) { "Marker with id ${marker.id} already exists." }
        markers.add(marker)
    }

    override fun deleteMarker(id: kotlin.uuid.Uuid): Boolean = markers.removeIf { it.id == id }
}
