package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import it.unibo.harmonikt.api.PointOfInterestAPI
import it.unibo.harmonikt.api.dto.PointOfInterestRegistrationDTO
import it.unibo.harmonikt.resources.PointOfInterests
import it.unibo.harmonikt.resources.PointOfInterests.Id.Markers

/**
 * Handles HTTP requests related to points of interest in the Robot Manager service.
 */
object PointOfInterestsHandlers {

    /**
     * Configures and sets up HTTP routing for handling point of interest (POI)-related requests.
     *
     * This function defines endpoints for accessing, creating, and deleting points of interest
     * in the system. These routes enable interaction with the points of interest through GET, POST,
     * and DELETE HTTP methods.
     *
     * @param repository The instance of the PointOfInterestRepository interface, which provides methods
     *                   for managing points of interest data.
     * @param client The HttpClient to use for making external HTTP requests needed within the handlers.
     */
    fun Routing.pointOfInterestsHandlers(repository: PointOfInterestAPI) {
        // GET /pois - Retrieve all points of interest
        get<PointOfInterests> {
            repository.getAllPointsOfInterest().fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { pois -> call.respondText("Points of Interests resource accessed: $pois") }
            )
        }

        // POST /pois - Create a new point of interest
        post<PointOfInterests> {
            val request = call.receive<PointOfInterestRegistrationDTO>()
            repository.registerPointOfInterest(request).fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { poi -> call.respondText("Point of Interests resource created: $poi") }
            )
        }

        // DELETE /pois/{poiId} - Delete a point of interest by ID
        delete<PointOfInterests.Id> { poiId ->
            repository.deletePointOfInterest(poiId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { poi -> call.respondText("Point of Interest with ID ${poiId.id} deleted successfully: $poi") }
            )
        }

        // GET /pois{poiId}/markers - Retrieve all markers for a specific point of interest
        get<PointOfInterests.Id> { poiId ->
            repository.getPointOfInterestMarkers(poiId).fold (
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { markers -> call.respondText("Markers for Point of Interest with ID ${poiId.id}: $markers") }
            )
        }

        // POST /pois/{poiId}/markers - Add a new marker to a point of interest
        post<PointOfInterests.Id> { poi ->
            TODO("Implement retrieval of point of interest by ID")
        }

        // GET /pois/{poiId}/markers/{markerId} - Retrieve a marker by ID
        get<Markers.Id> { marker ->
            val poiId: PointOfInterests.Id = marker.parent.parent
            repository.getMarkerInfo(poiId, marker).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { markerInfo -> call.respondText("Marker with ID ${marker.id} for Point of Interest with ID ${poiId.id}: $markerInfo") }
            )
        }

        // DELETE /pois/{poiId}/markers/{markerId} - Remove a marker from a point of interest
        delete<Markers.Id> { marker ->
            val poiId: PointOfInterests.Id = marker.parent.parent
            repository.removeMarker(poiId, marker).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { call.respondText("Marker with ID ${marker.id} dissociated from Point of Interest with ID ${marker.parent.parent} successfully") }
            )
        }
    }
}
