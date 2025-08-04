package it.unibo.harmonikt.handlers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import it.unibo.harmonikt.repository.PointOfInterestRepository
import it.unibo.harmonikt.resources.PointOfInterests

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
    fun Routing.pointOfInterestsHandlers(repository: PointOfInterestRepository, client: HttpClient) {
        get<PointOfInterests> { poi ->
            repository.equals(10)
            client.get("http://pois") // Example external call}
            call.respondText("Point of Interests resource accessed: $poi")
        }
        post<PointOfInterests> { point ->
            call.respondText("Point of Interests resource created with POST: $point")
        }
        delete<PointOfInterests> { poi ->
            call.respondText("Point of Interests resource deleted: $poi", status = HttpStatusCode.NoContent)
        }
    }
}
