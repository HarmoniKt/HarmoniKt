package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.api.PointOfInterestAPI
import it.unibo.harmonikt.api.dto.MarkerRegistrationDTO
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
     * @param pointOfInterest The instance of the PointOfInterestRepository interface, which provides methods
     *                   for managing points of interest data.
     * @param client The HttpClient to use for making external HTTP requests needed within the handlers.
     */
    fun Routing.pointOfInterestsHandlers(pointOfInterest: PointOfInterestAPI) {
        // GET /pois - Retrieve all points of interest
        get<PointOfInterests> {
            pointOfInterest.getAllPointsOfInterest().fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { pois -> call.respondText("Points of Interests resource accessed: $pois") },
            )
        }

        // POST /pois - Create a new point of interest
        post<PointOfInterests> {
            val request = call.receive<PointOfInterestRegistrationDTO>()
            pointOfInterest.registerPointOfInterest(request).fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { poi -> call.respondText("Point of Interests resource created: $poi") },
            )
        }

        // GET /pois/{poiId} - Retrieve a point of interest by ID
        get<PointOfInterests.Id> { poiId ->
            pointOfInterest.getPointOfInterest(poiId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { poi -> call.respondText("Point of Interest with ID ${poiId.id}: $poi") },
            )
        }

        // DELETE /pois/{poiId} - Delete a point of interest by ID
        delete<PointOfInterests.Id> { poiId ->
            pointOfInterest.deletePointOfInterest(poiId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { poi -> call.respondText("Point of Interest with ID ${poiId.id} deleted successfully: $poi") },
            )
        }

        // GET /pois/{poiId}/markers - Retrieve all markers for a specific point of interest
        get<Markers> { marker ->
            pointOfInterest.getPointOfInterestMarkers(marker.parent).fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { markers -> call.respondText("Markers for Point of Interest with ID ${marker.parent.id}: $markers") },
            )
        }

        // POST /pois/{poiId}/markers - Add a new marker to a point of interest
        post<Markers> { marker ->
            val request = call.receive<MarkerRegistrationDTO>()
            pointOfInterest.registerMarker(request).fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { markerId ->
                    call.respondText(
                        "Marker with ID ${markerId.id} created for Point of Interest with ID ${marker.parent.id}",
                    )
                },
            )
        }

        // GET /pois/{poiId}/markers/{markerId} - Retrieve a marker by ID
        get<Markers.Id> { marker ->
            val poiId: PointOfInterests.Id = marker.parent.parent
            pointOfInterest.getPointOfInterest(poiId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                {
                    pointOfInterest.getMarkerInfo(poiId, marker).fold(
                        { error -> call.respond(HttpStatusCode.NotFound, error) },
                        { markerInfo ->
                            call.respondText(
                                "Marker with ID ${marker.id} for POI with ID ${poiId.id}: $markerInfo",
                            )
                        },
                    )
                },
            )
        }

        // DELETE /pois/{poiId}/markers/{markerId} - Remove a marker from a point of interest
        delete<Markers.Id> { marker ->
            val poiId: PointOfInterests.Id = marker.parent.parent
            pointOfInterest.removeMarker(poiId, marker).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                {
                    call.respondText(
                        "Marker with ID ${marker.id} dissociated from POI with ID ${marker.parent.parent} successfully",
                    )
                },
            )
        }
    }
}
