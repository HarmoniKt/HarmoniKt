package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Marker.SpotMarker
import kotlin.uuid.Uuid

interface SpotMarkerRepository : MarkerRepository<SpotMarker>

class FakeSpotMarkerRepository : SpotMarkerRepository {
    private val markers = mutableListOf<SpotMarker>(
        SpotMarker(Uuid.random(), "Waypoint 1"),
        SpotMarker(Uuid.random(), "Waypoint 2"),
        SpotMarker(Uuid.random(), "Waypoint 3"),
    )

    override fun getMarkers(): List<SpotMarker> = markers

    override fun getMarkerById(id: Uuid): SpotMarker? = markers.find { it.id == id }

    override fun createMarker(spotMarker: SpotMarker) {
        if (getMarkerById(spotMarker.id) != null) {
            throw IllegalArgumentException("Marker with id ${spotMarker.id} already exists.")
        }
        markers.add(spotMarker)
    }

    override fun deleteMarker(id: Uuid): Boolean = markers.removeIf { it.id == id }
}
