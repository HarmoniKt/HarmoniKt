package it.unibo.harmonikt

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.handlers.MarkerHandlers.setupMarkerHandlers
import it.unibo.harmonikt.handlers.RobotHandlers.setupRobotHandlers
import it.unibo.harmonikt.repository.FakeMirMarkerRepository
import it.unibo.harmonikt.repository.FakeMirRobotRepository

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
    install(ContentNegotiation) {
        json()
    }

    val markerRepository = FakeMirMarkerRepository()
    val mirRobotRepository = FakeMirRobotRepository()

    routing {
        // Marker management endpoints
//        route("/marker") {
//            // Get all markers
//            get(handleGetMarkers)
//            // Create a new marker
//            post(handleCreateMarkers)
//            // Delete a marker
//            delete(handleDeleteMarkers)
//        }
//
//        // Mir management endpoints
//        route("/robot"){
//            post(handleRegisterRobot)
//            get(handleGetAllRobots)
//            get("available", handleGetAvailableRobots)
//            delete("{id}", handleUnregisterRobot)
//        }

        // Health check endpoint
        get("/") {
            call.respondText("Hello, world from MIR Service!")
        }
        setupRobotHandlers(mirRobotRepository)
        setupMarkerHandlers(markerRepository)
    }
}
