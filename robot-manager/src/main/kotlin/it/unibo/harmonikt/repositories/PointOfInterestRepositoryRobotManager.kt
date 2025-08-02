package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.repository.PointOfInterestRepository
import kotlin.uuid.Uuid

/**
 * A repository for managing points of interest in the Robot Manager service.
 * This class implements the PointOfInterestRepository interface, providing methods
 * to interact with points of interest data.
 */
class PointOfInterestRepositoryRobotManager : PointOfInterestRepository {
    override fun getPointsOfInterest(): List<PointOfInterest> {
        TODO("Not yet implemented")
    }

    override fun registerPointOfInterest(name: String, latitude: Float, longitude: Float): Boolean {
        TODO("Not yet implemented")
    }

    override fun deletePointOfInterest(id: Uuid): Boolean {
        TODO("Not yet implemented")
    }

    override fun associateMarker(poiId: Uuid, markerId: Uuid): Boolean {
        TODO("Not yet implemented")
    }

    override fun dissociateMarker(poiId: Uuid, markerId: Uuid): Boolean {
        TODO("Not yet implemented")
    }
}
