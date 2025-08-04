package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.Marker
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

    override fun registerPointOfInterest(name: String, latitude: Float, longitude: Float): Boolean =
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

    override fun associateMarker(poiId: Uuid, marker: Marker): Boolean =
        TODO("Not yet implemented")

    override fun dissociateMarker(poiId: Uuid, markerId: Uuid): Boolean {
        TODO("Not yet implemented")
    }
}
