package it.unibo.harmonikt

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.api.impl.PointOfInterestAPIImpl
import it.unibo.harmonikt.api.impl.RobotAPIImpl
import it.unibo.harmonikt.handlers.ActionsHandlers.setupActionsHandlers
import it.unibo.harmonikt.handlers.PointOfInterestsHandlers.pointOfInterestsHandlers
import it.unibo.harmonikt.handlers.RobotHandlers.setupRobotHandlers
import it.unibo.harmonikt.repositories.ActionRepositoryRobotManager
import it.unibo.harmonikt.repositories.PointOfInterestRepositoryRobotManager
import it.unibo.harmonikt.repositories.RobotRepositoryRobotManager
import it.unibo.harmonikt.repositories.mock.MockRepositoryRobotManager
import it.unibo.harmonikt.utils.ConsulRegisterService
import it.unibo.harmonikt.utils.KtorSetup.commonKtorSetup
import it.unibo.harmonikt.utils.KtorSetup.ktorClientSetup
import org.slf4j.LoggerFactory
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
    commonKtorSetup()

    val client = ktorClientSetup()

    val actionRepository = ActionRepositoryRobotManager()
    val pointOfInterestRepository = PointOfInterestRepositoryRobotManager()
    val robotRepository =
        if (System.getenv("MOCKED") == "true") MockRepositoryRobotManager() else RobotRepositoryRobotManager()
    val pointOfInterestAPI = PointOfInterestAPIImpl(pointOfInterestRepository, client)
    val robotApi = RobotAPIImpl(robotRepository, client) // actionRepository,

    routing {
        swaggerUI(path = "/swagger", swaggerFile = "openapi/harmonikt.yml") {}
//        get("/hello") {
//            val mirContent = client.get("http://mir-service/")
//            val spotContent = client.get("http://spot-service/robots")
//            call.respondText(
//                "This is the Robot Manager service! And can reach:\n" +
//                    "MIR Service Response: ${mirContent.bodyAsText()}\n" +
//                    "Spot Service Py Response: ${spotContent.bodyAsText()}",
//            )
//        }
        get("/") {
            call.respond(HttpStatusCode.OK)
        }

        setupActionsHandlers(actionRepository, client)
        pointOfInterestsHandlers(pointOfInterestAPI)
        setupRobotHandlers(robotApi)
    }
}
