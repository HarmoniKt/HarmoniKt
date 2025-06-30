package it.unibo.harmonikt

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import it.unibo.harmonikt.Handlers.handleCreateMarkers
import it.unibo.harmonikt.Handlers.handleDeleteMarkers
import it.unibo.harmonikt.Handlers.handleGetMarkers

/**
 * MIR service entrypoint.
 * This function starts the MIR service web server on port 8080, making it accessible from any network interface.
 * The server provides REST API endpoints for managing MIR robot markers.
 */
fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)
}

/**
 * Configures the Ktor application module for the MIR service.
 * Sets up routing for the various API endpoints provided by the service.
 */
private fun Application.module() {
    routing {
        // Marker management endpoints
        route("/marker") {
            // Get all markers
            get(handleGetMarkers)
            // Create a new marker
            post(handleCreateMarkers)
            // Delete a marker
            delete(handleDeleteMarkers)
        }

        // Health check endpoint
        get("/") {
            call.respondText("Hello, world from MIR Service!")
        }
    }
}
