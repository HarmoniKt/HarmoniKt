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
    routing {
        route("/marker") {
            get(handleGetMarkers)
            post(handleCreateMarkers)
            delete(handleDeleteMarkers)
        }
        get("/") {
            call.respondText("Hello, world from MIR Service!")
        }
    }
}
