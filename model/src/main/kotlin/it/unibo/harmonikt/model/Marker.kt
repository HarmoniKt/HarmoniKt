package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
sealed interface Marker {
    @Serializable
    data class MirMarker(val id: Uuid, val identifier: String) : Marker

    @Serializable
    data class SpotMarker(val id: Uuid, val waypoint: String) : Marker
}
