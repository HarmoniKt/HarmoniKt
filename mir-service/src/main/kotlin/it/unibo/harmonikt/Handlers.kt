package it.unibo.harmonikt

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import it.unibo.harmonikt.model.Marker.MirMarker
import it.unibo.harmonikt.MirRegistry

/**
 * Object containing handler functions for MIR service API endpoints.
 * These handlers process HTTP requests and generate appropriate responses.
 */
object Handlers {
    /**
     * Handler for GET /marker endpoint.
     * Retrieves all MIR markers in the system.
     *
     * Currently a placeholder that returns a NotImplemented status.
     */
    val handleGetMarkers: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Get markers functionality is not implemented yet.")
    }

    /**
     * Handler for POST /marker endpoint.
     * Creates a new MIR marker in the system.
     *
     * @throws CannotTransformContentToTypeException if the request body cannot be parsed as a MirMarker
     */
    val handleCreateMarkers: suspend RoutingContext.() -> Unit = {
        try {
            val markerToRegister = call.receive<MirMarker>()
            call.respond(HttpStatusCode.Created, markerToRegister)
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid marker data: ${e.message}")
        }
    }

    /**
     * Handler for DELETE /marker endpoint.
     * Deletes a MIR marker from the system.
     *
     * Currently a placeholder that returns a NotImplemented status.
     */
    val handleDeleteMarkers: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Delete markers functionality is not implemented yet.")
    }

    /**
     * Handler for POST /robot endpoint.
     * Register a new MIR Robot in the system.
     *
     * Currently, a placeholder that returns a NotImplemented status.
     */
    val handleRegisterRobot: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Delete markers functionality is not implemented yet.")
    }


    /**
     * Handler for Delete /robot endpoint.
     * Unregister a new MIR Robot in the system.
     *
     * Currently, a placeholder that returns a NotImplemented status.
     */
    val handleUnregisterRobot: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Delete markers functionality is not implemented yet.")
    }

    /**
     * Handler for GET /robot/available endpoint.
     * Get all the available robots, namely the ones not in a mission.
     *
     * Currently, a placeholder that returns a NotImplemented status.
     */
    val handleGetAvailableRobots: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Delete markers functionality is not implemented yet.")
    }

    /**
     * Handler for GET /robot endpoint.
     * Get all the robots, including the ones in a mission.
     *
     * Currently, a placeholder that returns a NotImplemented status.
     */
    val handleGetAllRobots: suspend RoutingContext.() -> Unit = {
        // This handler is a placeholder for future implementation
        call.respond(HttpStatusCode.NotImplemented, "Delete markers functionality is not implemented yet.")
    }

}
