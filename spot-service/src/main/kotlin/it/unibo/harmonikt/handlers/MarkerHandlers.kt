package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.repository.SpotMarkerRepository
import kotlin.uuid.Uuid

object MarkerHandlers {
    suspend fun RoutingContext.handleGetMarkers(repository: SpotMarkerRepository) {
        // This handler is a placeholder for future implementation
        val allMarkers = repository.getMarkers()
        call.respond(HttpStatusCode.OK, allMarkers)
    }

    suspend fun RoutingContext.handleCreateMarkers(repository: SpotMarkerRepository) {
        try {
            val markerToRegister = call.receive<Marker.SpotMarker>()
            repository.createMarker(markerToRegister)
            call.respond(HttpStatusCode.Created, "Created: $markerToRegister")
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid marker data: ${e.message}")
        }
    }

    suspend fun RoutingContext.handleDeleteMarkers(repository: SpotMarkerRepository, id: Uuid) {
        // This handler is a placeholder for future implementation
        if (repository.deleteMarker(id)) {
            call.respond(HttpStatusCode.OK, "Marker with id $id deleted successfully")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid marker id: $id")
        }
    }
}
