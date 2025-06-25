package it.unibo.harmonikt.endpoint

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import it.unibo.harmonikt.handlers.RobotHandlers.handleGetRobotById
import it.unibo.harmonikt.handlers.RobotHandlers.handleGetRobotPosition
import it.unibo.harmonikt.handlers.RobotHandlers.handleGetRobots
import it.unibo.harmonikt.handlers.RobotHandlers.handleMoveRobot
import it.unibo.harmonikt.repository.SpotMarkerRepository
import it.unibo.harmonikt.repository.SpotRobotRepository
import kotlin.uuid.Uuid

fun Application.configureRobotEndpoint(robotRepository: SpotRobotRepository, markerRepository: SpotMarkerRepository) {
    routing {
        route("/robots") {
            get {
                handleGetRobots(robotRepository)
            }

            route("/{robotId}") {
                get {
                    val robotId = Uuid.fromByteArray(
                        call.parameters["robotId"]?.encodeToByteArray() ?: error("Invalid robot ID"),
                    )
                    handleGetRobotById(robotRepository, robotId)
                }

                get("/position") {
                    val robotId = Uuid.fromByteArray(
                        call.parameters["robotId"]?.encodeToByteArray() ?: error("Invalid robot ID"),
                    )
                    handleGetRobotPosition(robotRepository, robotId)
                }

                post("/move") {
                    val robotId = Uuid.fromByteArray(
                        call.parameters["robotId"]?.encodeToByteArray() ?: error("Invalid robot ID"),
                    )
                    handleMoveRobot(robotRepository, markerRepository, robotId)
                }
            }
        }
    }
}
