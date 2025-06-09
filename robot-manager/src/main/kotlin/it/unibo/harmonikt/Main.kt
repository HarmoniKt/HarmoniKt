package it.unibo.harmonikt

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.resources.Resources
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import it.unibo.harmonikt.resources.Actions
import it.unibo.harmonikt.resources.PointOfInterests

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

    routing {
        get("/") {
            call.respondText("Hello, from Robot Manager!")
        }
        get<Actions> { actions ->
            call.respondText("Actions resource accessed: $actions")
        }
        post<Actions> { actions ->
            call.respondText("Actions resource created with POST: $actions")
        }
        get<Actions.Id> { actionId ->
            call.respondText("Action ID resource accessed with ID: ${actionId.id}")
        }

        get<PointOfInterests> { poi ->
            call.respondText("Point of Interests resource accessed: $poi")
        }
        post<PointOfInterests> { point ->
            call.respondText("Point of Interests resource created with POST: $point")
        }
        delete<PointOfInterests> { poi ->
            call.respondText("Point of Interests resource deleted: $poi", status = HttpStatusCode.NoContent)
        }
    }
}
