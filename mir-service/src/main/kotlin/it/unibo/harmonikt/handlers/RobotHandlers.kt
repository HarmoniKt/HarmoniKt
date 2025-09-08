package it.unibo.harmonikt.handlers

import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO.MirRobotRegistrationDTO
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
        get<MirRobots> {
            repository.getRobots()
        }

        post<MirRobots> {
            val request = call.receive<MirRobotRegistrationDTO>()
            repository.createRobot(request.canonicalName, request.apiToken, request.host)
        }

        delete<MirRobots.Id> { robot ->
            repository.deleteRobot(robot.id)
        }

        get<MirRobots.Id> { robot ->
            repository.getRobotById(robot.id)
        }

//        post<MirRobots.Id.Move> { moveRequest ->
//            call.respondText("Robot move command received for ID: ${moveRequest.parent.id}")
//        }
    }
}
