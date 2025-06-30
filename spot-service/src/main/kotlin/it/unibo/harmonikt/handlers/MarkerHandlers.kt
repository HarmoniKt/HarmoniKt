package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.repository.SpotMarkerRepository
import kotlin.uuid.Uuid

/**
 * Object containing handler functions for Spot service marker-related API endpoints.
 * These handlers process HTTP requests and generate appropriate responses for marker operations.
 */
object MarkerHandlers {
    /**
     * Handles requests to retrieve all Spot markers.
     * Gets all markers from the repository and returns them with an OK status.
     *
     * @param repository The repository to retrieve markers from.
     */
    suspend fun RoutingContext.handleGetMarkers(repository: SpotMarkerRepository) {
        val allMarkers = repository.getMarkers()
        call.respond(HttpStatusCode.OK, allMarkers)
    }

    /**
     * Handles requests to create a new Spot marker.
     * Parses the marker from the request body, adds it to the repository,
     * and returns a Created status with a confirmation message.
     *
     * @param repository The repository to add the marker to.
     * @throws CannotTransformContentToTypeException if the request body cannot be parsed as a SpotMarker.
     */
    suspend fun RoutingContext.handleCreateMarkers(repository: SpotMarkerRepository) {
        try {
            val markerToRegister = call.receive<Marker.SpotMarker>()
            repository.createMarker(markerToRegister)
            call.respond(HttpStatusCode.Created, "Created: $markerToRegister")
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid marker data: ${e.message}")
        }
    }

    /**
     * Handles requests to delete a Spot marker by ID.
     * Attempts to delete the marker from the repository and returns
     * an appropriate status and message based on the result.
     *
     * @param repository The repository to delete the marker from.
     * @param id The unique identifier of the marker to delete.
     */
    suspend fun RoutingContext.handleDeleteMarkers(repository: SpotMarkerRepository, id: Uuid) {
        if (repository.deleteMarker(id)) {
            call.respond(HttpStatusCode.OK, "Marker with id $id deleted successfully")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid marker id: $id")
        }
    }
}
