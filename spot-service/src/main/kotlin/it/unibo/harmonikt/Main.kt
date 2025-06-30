package it.unibo.harmonikt

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.endpoint.configureMarkerEndpoint
import it.unibo.harmonikt.endpoint.configureRobotEndpoint
import it.unibo.harmonikt.repository.FakeSpotMarkerRepository
import it.unibo.harmonikt.repository.FakeSpotRobotRepository
import it.unibo.harmonikt.utils.ConsulPlugin

/**
 * Spot service entrypoint.
 * This function starts the Spot service web server on port 8080, making it accessible from any network interface.
 * The server provides REST API endpoints for managing Spot robot markers and robots.
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
 * Configures the Ktor application module for the Spot service.
 * Sets up content negotiation, HTTP client with Consul service discovery,
 * repositories, and routing for the various API endpoints provided by the service.
 */
private fun Application.module() {
    // Install JSON content negotiation for request/response serialization
    install(ContentNegotiation) { json() }

    // Create HTTP client with Consul service discovery plugin
    val client = HttpClient(Apache) {
        install(ConsulPlugin)
    }

    // Initialize repositories
    val markerRepository = FakeSpotMarkerRepository()
    val robotRepository = FakeSpotRobotRepository()

    // Configure API endpoints
    configureMarkerEndpoint(markerRepository)
    configureRobotEndpoint(robotRepository, markerRepository)

    // Set up basic routing
    routing {
        // Health check endpoint
        get("/") {
            call.respondText("Hello, world from Spot Service!")
        }
    }
}
