package it.unibo.harmonikt.handlers

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.api.RobotAPI
import it.unibo.harmonikt.api.RobotCreationRequest
import it.unibo.harmonikt.api.dto.RobotInfoDTO
import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.resources.Robots
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Represents a request to create a new action for a robot.
 *
 * This data class is used to capture the necessary information required
 * to create an actionable command for a robot in the system. The command
 * defined in this request specifies the task or operation to be executed
 * by the robot.
 *
 * @property command The command string describing the action to be performed by the robot.
 */
@Serializable
data class ActionCreateRequest(val command: String)

/**
 * Represents the response for a robot action.
 *
 * This data class holds information about a specific action performed by a robot,
 * including the unique identifier of the action, the identifier of the robot
 * that performed the action, the command issued, and a timestamp indicating
 * when the action was executed.
 *
 * @property id The unique identifier of the action.
 * @property robotId The identifier of the robot that performed the action.
 * @property command The command issued as part of the action.
 * @property timestamp The timestamp marking when the action was executed.
 */
@Serializable
data class ActionResponse(val id: Uuid, val robotId: RobotId, val command: String, val timestamp: String)

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
     * @param client The HttpClient to be used for making any external HTTP requests required within the handlers.
     */
    fun Routing.setupRobotHandlers(robot: RobotAPI, client: HttpClient) {
        // GET /robots - Retrieve all active robots
        get<Robots> {
//            call.respond(RobotInfoDTO(Uuid.random(), "peppino", RobotType.SPOT))
            val spotsRobotInfoDTO = client.get("http://spot-service/robots").body<List<RobotInfoDTO>>()
//            val mirRobotInfoDTO = client.get("http://mir-service/robots").body<List<RobotInfoDTO>>()
            call.respond(spotsRobotInfoDTO)
        }

        // POST /robots - Add a new robot
        post<Robots> {
            val request = call.receive<RobotCreationRequest>()
            request.equals(0)
            TODO("Not yet implemented")
        }

        // GET /robots/{robotId} - Retrieve information about a robot
        get<Robots.Id> { robotId ->
            TODO("Not yet implemented")
        }

        // DELETE /robots/{robotId} - Remove a robot
        delete<Robots.Id> { robotId ->
            TODO("Not yet implemented")
        }

        // POST /robots/{robotId}/actions - Create a new action for a robot
        post<Robots.Id.Actions> { actions ->
            val action = call.receive<Action>()
            action.equals(0)
            TODO("Not yet implemented")
        }
    }
}
