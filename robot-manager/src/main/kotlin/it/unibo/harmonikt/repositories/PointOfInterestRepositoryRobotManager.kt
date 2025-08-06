package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.Marker.MirMarker
import it.unibo.harmonikt.model.Marker.SpotMarker
import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.repository.PointOfInterestRepository
import kotlin.uuid.Uuid

/**
 * A repository for managing points of interest in the Robot Manager service.
 * This class implements the PointOfInterestRepository interface, providing methods
 * to interact with points of interest data.
 */
class PointOfInterestRepositoryRobotManager : PointOfInterestRepository {
    private val pointsOfInterest = mutableListOf<PointOfInterest>()

    override fun getPointsOfInterest(): List<PointOfInterest> = pointsOfInterest

    override fun getPointOfInterestById(id: Uuid): PointOfInterest? = pointsOfInterest.find { it.id == id }

    override fun registerPointOfInterest(id: Uuid, name: String, latitude: Float, longitude: Float): Boolean =
        pointsOfInterest.add(
            PointOfInterest(
                id = Uuid.random(),
                name = name,
                latitude = latitude,
                longitude = longitude,
                associatedMarkers = emptyList(),
            ),
        )

    override fun deletePointOfInterest(id: Uuid): Boolean = pointsOfInterest.remove(getPointOfInterestById(id))

    override fun associateMarker(poiId: Uuid, marker: Marker): Boolean {
        val poi = pointsOfInterest.find { it.id == poiId } ?: return false
        val updatedPoi = poi.copy(associatedMarkers = poi.associatedMarkers + marker)
        pointsOfInterest.remove(poi)
        return pointsOfInterest.add(updatedPoi)
    }

    override fun getMarkerInfo(poiId: Uuid, markerId: Uuid): Marker? =
        pointsOfInterest.find { it.id == poiId }?.associatedMarkers?.find {
            when (it) {
                is MirMarker -> it.id == markerId
                is SpotMarker -> it.id == markerId
            }
        }

    override fun dissociateMarker(poiId: Uuid, markerId: Uuid): Boolean {
        val poi = pointsOfInterest.find { it.id == poiId } ?: return false
        val updatedPoi = poi.copy(
            associatedMarkers = poi.associatedMarkers.filterNot {
                when (it) {
                    is MirMarker -> it.id == markerId
                    is SpotMarker -> it.id == markerId
                }
            },
        )
        pointsOfInterest.remove(poi)
        return pointsOfInterest.add(updatedPoi)
    }
}
