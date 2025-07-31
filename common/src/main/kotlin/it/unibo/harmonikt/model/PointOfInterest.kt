package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Represents a location or area of interest in the environment.
 * Points of interest are used as destinations for robot navigation and can be associated with one or more markers.
 */
@Serializable
data class PointOfInterest(
    /**
     * Unique identifier for the point of interest.
     */
    val id: Uuid,

    /**
     * Canonical name of the point of interest.
     * This name is used to identify the point in a human-readable format.
     */
    val name: String,

    /**
     * Latitude of the point of interest.
     * This is a floating-point value representing the geographical latitude.
     */
    val latitude: Float,

    /**
     * Longitude of the point of interest.
     * This is a floating-point value representing the geographical longitude.
     */
    val longitude: Float,

    /**
     * The list of markers associated with this point of interest.
     * Different robot types may use different marker types to identify the same location.
     */
    val associatedMarkers: List<Marker>,
)
