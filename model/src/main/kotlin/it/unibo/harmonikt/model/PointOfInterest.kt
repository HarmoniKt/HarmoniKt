package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable

@Serializable
data class PointOfInterest(val associatedMarkers: List<Marker>)
