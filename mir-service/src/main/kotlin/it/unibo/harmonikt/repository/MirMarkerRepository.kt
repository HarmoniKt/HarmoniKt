package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Marker.MirMarker
import kotlin.uuid.Uuid

/**
 * Repository interface for managing MirMarker entities.
 * This interface extends the generic MarkerRepository to handle Mir-specific markers.
 */
interface MirMarkerRepository : MarkerRepository<MirMarker>

/**
 * Fake implementation of MirMarkerRepository for testing purposes.
 * Stores markers in memory.
 */
class FakeMirMarkerRepository : MirMarkerRepository {
    private val markers = mutableListOf(
        MirMarker(id = Uuid.random(), identifier = "Waypoint 1"),
        MirMarker(id = Uuid.random(), identifier = "Waypoint 2"),
        MirMarker(id = Uuid.random(), identifier = "Waypoint 3"),
    )

    override fun getMarkers(): List<MirMarker> = markers

    override fun getMarkerById(id: Uuid): MirMarker? = markers.find { it.id == id }

    override fun registerMarker(marker: MirMarker): Boolean {
        requireNotNull(getMarkerById(marker.id)) { "Marker with id ${marker.id} already exists." }
        return markers.add(marker)
    }

    override fun deleteMarker(id: Uuid): Boolean = markers.removeIf { it.id == id }
}
