package it.unibo.harmonikt.repository

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
    fun getPointsOfInterest(): List<PointOfInterest>

    fun registerPointOfInterest(name: String, latitude: Float, longitude: Float): Boolean

    fun deletePointOfInterest(id: Uuid): Boolean

    fun associateMarker(poiId: Uuid, markerId: Uuid): Boolean

    fun dissociateMarker(poiId: Uuid, markerId: Uuid): Boolean
}
