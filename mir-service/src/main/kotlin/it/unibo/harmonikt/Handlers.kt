package it.unibo.harmonikt

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import it.unibo.harmonikt.model.Marker.MirMarker

object Handlers {
    val handleGetMarkers: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Get markers functionality is not implemented yet.")
    }
    val handleCreateMarkers: suspend RoutingContext.() -> Unit = {
        try {
            val markerToRegister = call.receive<MirMarker>()
            call.respond(HttpStatusCode.Created, markerToRegister)
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid marker data: ${e.message}")
        }
    }
    val handleDeleteMarkers: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Get markers functionality is not implemented yet.")
    }
}
