package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.api.RobotAPI
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO
import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.resources.Robots

/**
 * Handles HTTP requests related to robots in the Robot Manager service.
 */
object RobotHandlers {
    /**
     * Configures and sets up HTTP routing for handling robot-related requests.
     *
     * This function defines endpoints for managing robots, such as retrieving, creating, deleting robots,
     * and managing actions associated with a specific robot.
     *
     * @param robot The instance of the RobotAPI interface, which provides methods for interacting with robot data.
     */
    fun Routing.setupRobotHandlers(robot: RobotAPI) {
        // GET /robots - Retrieve all active robots
        get<Robots> {
            robot.getAllRobots().fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { robots -> call.respond(robots) },
            )
        }

        // POST /robots - Add a new robot
        post<Robots> {
            val request = call.receive<RobotRegistrationDTO>()
            robot.registerNewRobot(request).fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { robotId -> call.respond(robotId) },
            )
        }

        // GET /robots/{robotId} - Retrieve information about a robot
        get<Robots.Id> { robotId ->
            robot.getRobotById(robotId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { robotStatus -> call.respond(robotStatus) },
            )
        }

        // DELETE /robots/{robotId} - Remove a robot
        delete<Robots.Id> { robotId ->
            robot.deleteRobot(robotId).fold(
                { error -> call.respond(HttpStatusCode.NotFound, error) },
                { _ -> call.respond(HttpStatusCode.NoContent) },
            )
        }

        // POST /robots/{robotId}/actions - Create a new action for a robot
        post<Robots.Id.Actions> { actions ->
            robot.createRobotAction(actions.parent.robotId, call.receive<Action>()).fold(
                { error -> call.respond(HttpStatusCode.InternalServerError, error) },
                { action -> call.respond(action) },
            )
        }
    }
}
