package it.unibo.harmonikt

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import it.unibo.harmonikt.utils.ConsulRegisterService
import it.unibo.harmonikt.utils.KtorSetup.commonKtorSetup
import java.net.InetAddress

/**
 * Group mission manager service entrypoint.
 */
fun main() {
    val logger = org.slf4j.LoggerFactory.getLogger("it.unibo.harmonikt.group-mission-manager")
    logger.info("Starting Group Mission Manager service...")
    val server = embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    )
    ConsulRegisterService.registerConsulService(
        System.getenv("CONSUL_URL") ?: "http://localhost:8500",
        "group-mission-manager",
        InetAddress.getLocalHost().hostName,
        server.engineConfig.connectors[0].port,
    )

    server.start(wait = true)
}

private fun Application.module() {
    commonKtorSetup()

//    val client = ktorClientSetup()

    routing {
        get("/") {
            call.respondText("Hello, world from Mission Manager!")
        }
    }
}
