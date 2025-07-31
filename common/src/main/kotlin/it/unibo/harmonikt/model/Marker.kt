package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Represents a marker in the environment that can be recognized by robots.
 * Markers are used to identify specific locations or points of interest.
 * Different robot types may use different marker implementations.
 */
@Serializable
sealed interface Marker {
    /**
     * Represents a marker specifically for MIR robots.
     * MIR robots use string identifiers to recognize markers.
     */
    @Serializable
    data class MirMarker(
        /**
         * Unique identifier for the marker.
         */
        val id: Uuid,

        /**
         * String identifier used by MIR robots to recognize this marker.
         */
        val identifier: String,
    ) : Marker

    /**
     * Represents a marker specifically for Spot robots.
     * Spot robots use waypoint strings to recognize markers.
     */
    @Serializable
    data class SpotMarker(
        /**
         * Unique identifier for the marker.
         */
        val id: Uuid,

        /**
         * Fiducial number used by Spot robots to recognize this marker.
         */
        val fiducial: Int,
    ) : Marker
}
