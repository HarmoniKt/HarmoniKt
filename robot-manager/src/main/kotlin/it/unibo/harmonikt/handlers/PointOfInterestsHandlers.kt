package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import it.unibo.harmonikt.repository.PointOfInterestRepository
import it.unibo.harmonikt.resources.PointOfInterests

object PointOfInterestsHandlers {
    fun Routing.pointOfInterestsHandlers(repository: PointOfInterestRepository) {
        get<PointOfInterests> { poi ->
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
