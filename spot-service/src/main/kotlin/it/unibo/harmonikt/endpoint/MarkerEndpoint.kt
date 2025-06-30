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

/**
 * Configures the marker-related endpoints for the Spot service.
 * Sets up routes for retrieving, creating, and deleting Spot markers.
 *
 * @param repository The repository used to store and retrieve Spot markers.
 */
fun Application.configureMarkerEndpoint(repository: SpotMarkerRepository) {
    routing {
        route("/markers") {
            // GET /markers - Retrieve all markers
            get {
                handleGetMarkers(repository)
            }

            // POST /markers - Create a new marker
            post {
                handleCreateMarkers(repository)
            }

            // DELETE /markers/{id} - Delete a marker by ID
            delete("{id}") {
                // Parse the ID from the path parameter
                val id = Uuid.fromByteArray(call.parameters["id"]?.encodeToByteArray() ?: error("Invalid id"))
                handleDeleteMarkers(repository, id)
            }
        }
    }
}
