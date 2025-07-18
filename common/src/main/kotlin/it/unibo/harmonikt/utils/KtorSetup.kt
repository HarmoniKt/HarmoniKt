package it.unibo.harmonikt.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.resources.Resources
import org.slf4j.event.Level

object KtorSetup {
    /**
     * Common Ktor setup for applications.
     * This function installs common plugins and configurations for Ktor applications.
     * It includes content negotiation, logging, CORS, and an HTTP client with Consul service discovery.
     */
    fun Application.commonKtorSetup() {
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
    }

    /**
     * Sets up an HTTP client for Ktor applications.
     *
     * This function configures an HTTP client with the Apache engine,
     * installs the Consul plugin for service discovery,
     * and sets up retry logic for HTTP requests.
     *
     * @return An instance of [HttpClient] configured for Ktor applications.
     */
    fun ktorClientSetup(): HttpClient = HttpClient(Apache) {
        install(ConsulPlugin)
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 5)
            exponentialDelay()
        }
    }
}
