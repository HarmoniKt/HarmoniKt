package it.unibo.harmonikt.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.resources.Resources
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO
import kotlinx.serialization.json.Json
import org.slf4j.event.Level
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationClient
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationServer

/**
 * Object providing utility methods for setting up Ktor applications and HTTP clients.
 * This includes common configurations such as content negotiation, logging, CORS setup,
 * and the creation of an HTTP client with service discovery capabilities using the Consul plugin.
 */
object KtorSetup {
    /**
     * Common Ktor setup for applications.
     * This function installs common plugins and configurations for Ktor applications.
     * It includes content negotiation, logging, CORS, and an HTTP client with Consul service discovery.
     */
    fun Application.commonKtorSetup() {
        install(Resources)
        install(ContentNegotiationServer) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(RequestValidation) {
            validate<RobotRegistrationDTO>(RobotRegistrationDTO::validate)
        }
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
        expectSuccess = true
        install(ConsulPlugin)
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 5)
            exponentialDelay()
        }
        install(ContentNegotiationClient) {
            json() // Example: Register JSON content transformation
        }
    }
}
