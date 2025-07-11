package it.unibo.harmonikt.handlers

import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.repository.MirRobotRepository
import it.unibo.harmonikt.resources.MirRobots

/**
 * Handles HTTP requests related to robots in the MIR service.
 */
object RobotHandlers {
    /**
     * Sets up the routing for robot-related endpoints.
     *
     * @param repository The MirRobotRepository instance to handle robot data.
     */
    fun Routing.setupRobotHandlers(repository: MirRobotRepository) {
        get<MirRobots> { robot ->
            repository.equals(10)
            call.respondText("Robot resource accessed: $robot")
        }
        post<MirRobots> { robot ->
            call.respondText("Robot resource created with POST: $robot")
        }
        delete<MirRobots> { robot ->
            call.respondText("Robot resource deleted: $robot")
        }
        get<MirRobots.Id> { robotId ->
            call.respondText("Robot resource accessed with ID: ${robotId.id}")
        }
        post<MirRobots.Id.Move> { moveRequest ->
            call.respondText("Robot move command received for ID: ${moveRequest.parent.id}")
        }
    }
}
