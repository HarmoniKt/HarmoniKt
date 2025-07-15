package it.unibo.harmonikt

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.handlers.MarkerHandlers.setupMarkerHandlers
import it.unibo.harmonikt.handlers.RobotHandlers.setupRobotHandlers
import it.unibo.harmonikt.repository.FakeSpotMarkerRepository
import it.unibo.harmonikt.repository.FakeSpotRobotRepository
import it.unibo.harmonikt.utils.ConsulRegisterService
import org.slf4j.event.Level
import java.net.InetAddress

/**
 * Spot service entrypoint.
 * This function starts the Spot service web server on port 8080, making it accessible from any network interface.
 * The server provides REST API endpoints for managing Spot robot markers and robots.
 */
fun main() {
    val server = embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    )
    ConsulRegisterService.registerConsulService(
        System.getenv("CONSUL_URL") ?: "http://localhost:8500",
        "spot-service",
        InetAddress.getLocalHost().hostName,
        server.engineConfig.connectors[0].port,
    )
    server.start(wait = true)
}

/**
 * Configures the Ktor application module for the Spot service.
 * Sets up content negotiation, HTTP client with Consul service discovery,
 * repositories, and routing for the various API endpoints provided by the service.
 */
private fun Application.module() {
    // Install JSON content negotiation for request/response serialization
    install(Resources)
    install(ContentNegotiation) { json() }
    install(CallLogging) {
        level = Level.INFO
        // Log all requests
    }

    // Create HTTP client with Consul service discovery plugin
//    val client = HttpClient(Apache) {
//        install(ConsulPlugin)
//    }

    // Initialize repositories
    val markerRepository = FakeSpotMarkerRepository()
    val robotRepository = FakeSpotRobotRepository()

    // Set up basic routing
    routing {
        // Health check endpoint
        get("/") {
            call.respondText("Hello, world from Spot Service!")
        }
        setupRobotHandlers(robotRepository)
        setupMarkerHandlers(markerRepository)
    }
}
