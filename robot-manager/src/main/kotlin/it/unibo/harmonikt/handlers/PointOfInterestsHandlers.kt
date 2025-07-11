package it.unibo.harmonikt.handlers

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
     * Sets up the routing for points of interest-related endpoints.
     *
     * @param repository The PointOfInterestRepository instance to handle points of interest data.
     */
    fun Routing.pointOfInterestsHandlers(repository: PointOfInterestRepository) {
        get<PointOfInterests> { poi ->
            repository.equals(10)
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
