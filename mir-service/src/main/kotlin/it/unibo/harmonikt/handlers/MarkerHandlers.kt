package it.unibo.harmonikt.handlers

import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.repository.MirMarkerRepository
import it.unibo.harmonikt.resources.MirMarkers

/**
 * Handles HTTP requests related to markers in the MIR service.
 */
object MarkerHandlers {
    /**
     * Sets up the routing for marker-related endpoints.
     *
     * @param repository The MirMarkerRepository instance to handle marker data.
     */
    fun Routing.setupMarkerHandlers(repository: MirMarkerRepository) {
        get<MirMarkers> { markers ->
            repository.equals(0)
            call.respondText("Markers resource accessed: $markers")
        }
        post<MirMarkers> { markers ->
            call.respondText("Markers resource created with POST: $markers")
        }
        post<MirMarkers.Id> { markerId ->
            call.respondText("Marker with ID ${markerId.id} processed")
        }
        get<MirMarkers.Id> { markers ->
            call.respondText("Marker with ID ${markers.id} retrieved")
        }
        delete<MirMarkers.Id> { markerId ->
            call.respondText("Marker with ID ${markerId.id} deleted")
        }
        get<MirMarkers.Id.Position> { markerPosition ->
            call.respondText("Marker position for ID ${markerPosition.parent.id} retrieved")
        }
    }
}
