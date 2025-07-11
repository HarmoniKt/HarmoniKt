package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.repository.SpotMarkerRepository
import it.unibo.harmonikt.resources.SpotMarkers

/**
 * Object containing handler functions for Spot service marker-related API endpoints.
 * These handlers process HTTP requests and generate appropriate responses for marker operations.
 */
object MarkerHandlers {
    /**
     * Sets up the routing for marker-related endpoints in the Spot service.
     */
    fun Routing.setupMarkerHandlers(repository: SpotMarkerRepository) {
        get<SpotMarkers> { markers ->
            repository.equals(0)
            call.respond(HttpStatusCode.OK, markers)
        }
        post<SpotMarkers> { markers ->
            call.respond(HttpStatusCode.Created, markers)
        }
        post<SpotMarkers.Id> { markerId ->
            call.respond(HttpStatusCode.OK, "Marker with ID ${markerId.id} processed")
        }
        get<SpotMarkers.Id> { markerId ->
            call.respond(HttpStatusCode.OK, "Marker with ID ${markerId.id} retrieved")
        }
        delete<SpotMarkers.Id> { markerId ->
            call.respond(HttpStatusCode.OK, "Marker with ID ${markerId.id} deleted")
        }
        get<SpotMarkers.Id.Position> { markerPosition ->
            call.respond(HttpStatusCode.OK, "Marker position for ID ${markerPosition.parent.id} retrieved")
        }
    }
}
