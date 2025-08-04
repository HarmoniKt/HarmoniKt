package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.api.MarkerAPI
import it.unibo.harmonikt.resources.Markers

object MarkersHandler {
    fun Routing.setupMarkersHandlers(marker: MarkerAPI) {
        // GET /markers - Retrieve all markers
        get<Markers> {
            marker.getAllMarkers().fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { markers -> call.respond(markers) },
            )
        }

        // POST /markers - Add a new marker
        post<Markers> {
            val request = call.receive<it.unibo.harmonikt.api.dto.MarkerRegistrationDTO>()
            marker.registerNewMarker(request).fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { markerId -> call.respond(markerId) },
            )
        }

        // GET /markers/{markerId} - Retrieve information about a marker
        get<Markers.Id> { markerId ->
            marker.getMarkerInfo(markerId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { markerInfo -> call.respond(markerInfo) },
            )
        }

        // DELETE /markers/{markerId} - Remove a marker
        delete<Markers.Id> { markerId ->
            marker.deleteMarker(markerId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { _ -> call.respond(HttpStatusCode.NoContent) },
            )
        }
    }
}
