package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.Marker.MirMarker
import it.unibo.harmonikt.model.Marker.SpotMarker
import it.unibo.harmonikt.repository.MarkerRepository
import kotlin.uuid.Uuid

/**
 * Repository implementation for managing markers in the robot manager.
 * This class provides methods to register, retrieve, and delete markers
 * that can be recognized by robots.
 */
class MarkerRepositoryRobotManager : MarkerRepository<Marker> {
    private val registeredMarkers = mutableListOf<Marker>()

    override fun getMarkers(): List<Marker> = registeredMarkers

    override fun getMarkerById(id: Uuid): Marker? = registeredMarkers.find {
        when (it) {
            is SpotMarker -> it.id == id
            is MirMarker -> it.id == id
        }
    }

    override fun registerMarker(marker: Marker) = registeredMarkers.add(marker)

    override fun deleteMarker(id: Uuid): Boolean = registeredMarkers.removeIf {
        when (it) {
            is SpotMarker -> it.id == id
            is MirMarker -> it.id == id
        }
    }
}
