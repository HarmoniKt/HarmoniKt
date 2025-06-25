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
import it.unibo.harmonikt.utils.ConsulPlugin

/**
 * Group mission manager service entrypoint.
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
    install(ContentNegotiation) { json() }

    val client = HttpClient(Apache) {
        install(ConsulPlugin)
    }

    routing {
        get("/") {
            call.respondText("Hello, world from Mission Manager!")
        }
    }
}
