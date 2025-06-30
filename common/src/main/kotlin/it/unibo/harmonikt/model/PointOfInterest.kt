package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable

/**
 * Represents a location or area of interest in the environment.
 * Points of interest are used as destinations for robot navigation and can be associated with one or more markers.
 */
@Serializable
data class PointOfInterest(
    /**
     * The list of markers associated with this point of interest.
     * Different robot types may use different marker types to identify the same location.
     */
    val associatedMarkers: List<Marker>
)
