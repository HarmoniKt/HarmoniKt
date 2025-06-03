package it.unibo.harmonikt.endpoint

import io.ktor.server.application.Application
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import it.unibo.harmonikt.handlers.MarkerHandlers.handleCreateMarkers
import it.unibo.harmonikt.handlers.MarkerHandlers.handleDeleteMarkers
import it.unibo.harmonikt.handlers.MarkerHandlers.handleGetMarkers
import it.unibo.harmonikt.repository.SpotMarkerRepository
import kotlin.uuid.Uuid

fun Application.configureMarkerEndpoint(repository: SpotMarkerRepository) {
    routing {
        route("/marker") {
            get { handleGetMarkers(repository) }
            post { handleCreateMarkers(repository) }
            delete("{id}") {
                val id = Uuid.fromByteArray(call.parameters["id"]?.encodeToByteArray() ?: error("Invalid id"))
                handleDeleteMarkers(repository, id)
            }
        }
    }
}
