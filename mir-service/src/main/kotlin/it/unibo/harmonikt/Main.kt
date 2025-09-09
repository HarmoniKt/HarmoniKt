package it.unibo.harmonikt

import io.ktor.client.HttpClient
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
import it.unibo.harmonikt.repository.FakeMirMarkerRepository
import it.unibo.harmonikt.repository.MirRobotRepositoryImpl
import it.unibo.harmonikt.utils.ConsulRegisterService
import it.unibo.harmonikt.utils.KtorSetup.commonKtorSetup
import it.unibo.harmonikt.utils.KtorSetup.ktorClientSetup
import org.slf4j.event.Level
import java.net.InetAddress

/**
 * MIR service entrypoint.
 * This function starts the MIR service web server on port 8080, making it accessible from any network interface.
 * The server provides REST API endpoints for managing MIR robot markers.
 */
fun main() {
    val logger = org.slf4j.LoggerFactory.getLogger("it.unibo.harmonikt.mir-service")
    logger.info("Starting MIR service...")
    val server = embeddedServer(
        Netty,
        port = 8090,
        host = "0.0.0.0",
        module = Application::module,
    )
    ConsulRegisterService.registerConsulService(
        System.getenv("CONSUL_URL") ?: "http://localhost:8500",
        "mir-service",
        InetAddress.getLocalHost().hostName,
        server.engineConfig.connectors[0].port,
    )

    server.start(wait = true)
}

/**
 * Configures the Ktor application module for the MIR service.
 * Sets up routing for the various API endpoints provided by the service.
 */
private fun Application.module() {
    commonKtorSetup()
    val client = ktorClientSetup()

    val markerRepository = FakeMirMarkerRepository()
    val mirRobotRepository = MirRobotRepositoryImpl(client)

    routing {
        // Health check endpoint
        get("/") {
            call.respondText("Hello, world from MIR Service!")
        }
        setupRobotHandlers(mirRobotRepository)
        setupMarkerHandlers(markerRepository)
    }
}
