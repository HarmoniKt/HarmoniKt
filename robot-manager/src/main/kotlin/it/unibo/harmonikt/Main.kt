package it.unibo.harmonikt

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.resources.Resources
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.handlers.ActionsHandlers.setupActionsHandlers
import it.unibo.harmonikt.handlers.PointOfInterestsHandlers.pointOfInterestsHandlers
import it.unibo.harmonikt.repositories.ActionRepositoryRobotManager
import it.unibo.harmonikt.repositories.PointOfInterestRepositoryRobotManager
import it.unibo.harmonikt.utils.ConsulPlugin

/**
 * Robot manager service entrypoint.
 */
fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)
}

private fun Application.module() {
    install(Resources)
    install(ContentNegotiation) { json() }
    install(RequestValidation)

//    val client = HttpClient(Apache) {
//        install(ConsulPlugin)
//    }

    val actionRepository = ActionRepositoryRobotManager()
    val pointOfInterestRepository = PointOfInterestRepositoryRobotManager()

    routing {
        get("/") {
            call.respondText("Hello, from Robot Manager!")
        }

        setupActionsHandlers(actionRepository)
        pointOfInterestsHandlers(pointOfInterestRepository)
    }
}
