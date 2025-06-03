package it.unibo.harmonikt.repository

import it.unibo.harmonikt.model.Marker
import kotlin.uuid.Uuid

interface MarkerRepository<M : Marker> {
    fun getMarkers(): List<M>
    fun getMarkerById(id: Uuid): M?
    fun createMarker(spotMarker: M)
    fun deleteMarker(id: Uuid): Boolean
}
