package it.unibo.harmonikt

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.api.RobotAPIImpl
import it.unibo.harmonikt.handlers.ActionsHandlers.setupActionsHandlers
import it.unibo.harmonikt.handlers.PointOfInterestsHandlers.pointOfInterestsHandlers
import it.unibo.harmonikt.handlers.RobotHandlers.setupRobotHandlers
import it.unibo.harmonikt.repositories.ActionRepositoryRobotManager
import it.unibo.harmonikt.repositories.PointOfInterestRepositoryRobotManager
import it.unibo.harmonikt.repositories.RobotRepositoryRobotManager
import it.unibo.harmonikt.utils.ConsulPlugin
import it.unibo.harmonikt.utils.ConsulRegisterService
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import java.net.InetAddress

/**
 * Robot manager service entrypoint.
 */
fun main() {
    val logger = LoggerFactory.getLogger("it.unibo.harmonikt.robot-manager")
    logger.info("Starting Robot Manager service...")

    val server = embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    )
    ConsulRegisterService.registerConsulService(
        System.getenv("CONSUL_URL") ?: "http://localhost:8500",
        "robot-manager",
        InetAddress.getLocalHost().hostName,
        server.engineConfig.connectors[0].port,
    )
    server.start(wait = true)
}

private fun Application.module() {
    // Create a logger for this module
    val logger = LoggerFactory.getLogger("it.unibo.harmonikt.robot-manager")

    install(Resources)
    install(ContentNegotiation) { json() }
    install(RequestValidation)
    install(CallLogging) {
        level = Level.INFO
        // Log all requests
    }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }

    val client = HttpClient(Apache) {
        install(ConsulPlugin)
    }

    val actionRepository = ActionRepositoryRobotManager()
    val pointOfInterestRepository = PointOfInterestRepositoryRobotManager()
    val robotRepository = RobotRepositoryRobotManager()
    val robotApi = RobotAPIImpl(robotRepository, actionRepository)

    routing {
        swaggerUI(path = "/swagger", swaggerFile = "openapi/harmonikt.yml") {}
        get("/hello") {
            val mirContent = client.get("http://mir-service/")
            val spotContent = client.get("http://spot-service/")
            call.respondText(
                "This is the Robot Manager service! And can reach:\n" +
                    "MIR Service Response: ${mirContent.bodyAsText()}\n" +
                    "Spot Service Response: ${spotContent.bodyAsText()}",
            )
        }
        get("/") {
            call.respond(HttpStatusCode.OK)
        }

        setupActionsHandlers(actionRepository, client)
        pointOfInterestsHandlers(pointOfInterestRepository, client)
        setupRobotHandlers(robotApi, client)
    }
}
