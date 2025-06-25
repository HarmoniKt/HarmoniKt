package it.unibo.harmonikt.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import it.unibo.harmonikt.repository.SpotMarkerRepository
import it.unibo.harmonikt.repository.SpotRobotRepository
import kotlin.uuid.Uuid

object RobotHandlers {
    suspend fun RoutingContext.handleGetRobots(repository: SpotRobotRepository) {
        val allRobots = repository.getRobots()
        call.respond(HttpStatusCode.OK, allRobots)
    }

    suspend fun RoutingContext.handleGetRobotById(repository: SpotRobotRepository, id: Uuid) {
        val robot = repository.getRobotById(id)
        if (robot != null) {
            call.respond(HttpStatusCode.OK, robot)
        } else {
            call.respond(HttpStatusCode.NotFound, "Robot with id $id not found")
        }
    }

    suspend fun RoutingContext.handleGetRobotPosition(repository: SpotRobotRepository, id: Uuid) {
        val robot = repository.getRobotById(id)
        if (robot != null) {
            call.respond(HttpStatusCode.OK, robot.currentPosition)
        } else {
            call.respond(HttpStatusCode.NotFound, "Robot with id $id not found")
        }
    }

    suspend fun RoutingContext.handleMoveRobot(
        robotRepository: SpotRobotRepository,
        markerRepository: SpotMarkerRepository,
        robotId: Uuid,
    ) {
        try {
            // Define a data class for the move request
            data class MoveRequest(val targetMarkerId: Uuid)

            val moveRequest = call.receive<MoveRequest>()
            val robot = robotRepository.getRobotById(robotId)
            val marker = markerRepository.getMarkerById(moveRequest.targetMarkerId)

            if (robot == null) {
                call.respond(HttpStatusCode.NotFound, "Robot with id $robotId not found")
                return
            }

            if (marker == null) {
                call.respond(HttpStatusCode.NotFound, "Marker with id ${moveRequest.targetMarkerId} not found")
                return
            }

            // In a real implementation, this would send a command to the robot
            // For now, we'll just update the robot's state to ON_MISSION
            robotRepository.updateRobotState(robotId, it.unibo.harmonikt.model.RobotState.ON_MISSION)

            call.respond(
                HttpStatusCode.Accepted,
                "Move command accepted for robot $robotId to marker ${moveRequest.targetMarkerId}",
            )
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid move request: ${e.message}")
        }
    }
}
