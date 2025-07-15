package it.unibo.harmonikt

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.utils.ConsulRegisterService
import org.slf4j.event.Level
import java.net.InetAddress

/**
 * Map Manager service entrypoint.
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
        "map-manager",
        InetAddress.getLocalHost().hostName,
        server.engineConfig.connectors[0].port,
    )
    server.start(wait = true)
}

private fun Application.module() {
    install(ContentNegotiation) { json() }
    install(CallLogging) {
        level = Level.INFO
        // Log all requests
    }

//    val client = HttpClient(Apache) {
//        install(ConsulPlugin)
//    }

    routing {
        get("/") {
            call.respondText("Hello, world from Map Manager!")
        }
    }
}
