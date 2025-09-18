package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.api.dto.RobotIdDTO
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO.MirRobotRegistrationDTO
import it.unibo.harmonikt.api.dto.RobotStatusDTO
import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotType
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
        // GET /robots - Retrieve all active robots
        get<MirRobots> {
            val robots = repository.getRobots()
            call.respond(robots)
        }

        // POST /robots - Add a new robot
        post<MirRobots> {
            val request = call.receive<MirRobotRegistrationDTO>()
            val robotId = repository.createRobot(
                request.canonicalName,
                request.apiToken,
                request.host,
            )
            if (robotId != null) {
                call.respond(HttpStatusCode.Created, RobotIdDTO(robotId))
            } else {
                call.respond(HttpStatusCode.Conflict, "Robot ${request.canonicalName} not created.")
            }
        }

        // DELETE /robots/{robotId} - Remove a robot
        delete<MirRobots.Id> { robot ->
            val done = repository.deleteRobot(robot.id)
            if (done) {
                call.respond(HttpStatusCode.OK, "Robot with id ${robot.id} deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Delete failed for robot with id ${robot.id}, possibly not found")
            }
        }

        // GET /robots/{robotId} - Retrieve information about a robot
        get<MirRobots.Id> { r ->
            val robot: Robot? = repository.getRobotById(r.id)
            if (robot != null) {
                val dto = RobotStatusDTO(
                    id = robot.id,
                    canonicalName = robot.name,
                    state = robot.currentState,
                    batteryLevel = robot.batteryLevel,
                    currentPosition = robot.currentPosition,
                    type = RobotType.MIR,
                )
                call.respond(dto)
            } else {
                call.respond(HttpStatusCode.NotFound, "Robot with id ${r.id} not found")
            }
        }

        // POST /robots/{robotId}/actions - Send an action to a robot
        post<MirRobots.Id.Move> { action ->

        }
    }
}
