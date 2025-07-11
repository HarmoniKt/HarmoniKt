package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.repository.SpotRobotRepository
import it.unibo.harmonikt.resources.SpotRobots

/**
 * Object containing handler functions for robot-related HTTP endpoints.
 */
object RobotHandlers {
    /**
     * Sets up the routing for robot-related endpoints.
     */
    fun Routing.setupRobotHandlers(repository: SpotRobotRepository) {
        get<SpotRobots> { robots ->
            repository.equals(10)
            call.respondText("Robots resource accessed: $robots")
        }
        post<SpotRobots> { robots ->
            call.respondText("Robots resource created with POST: $robots")
        }
        delete<SpotRobots.Id> { robotId ->
            call.respondText("Robot resource deleted with ID: ${robotId.id}", status = HttpStatusCode.NoContent)
        }
        get<SpotRobots.Id.Position> { robotPosition ->
            call.respondText("Robot position accessed for ID: ${robotPosition.parent.id}")
        }
        post<SpotRobots.Id.Move> { moveRequest ->
            call.respondText("Robot move command received for ID: ${moveRequest.parent.id}")
        }
    }
}
