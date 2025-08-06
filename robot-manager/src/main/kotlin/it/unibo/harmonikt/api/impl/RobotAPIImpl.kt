package it.unibo.harmonikt.api.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import it.unibo.harmonikt.api.RobotAPI
import it.unibo.harmonikt.api.RobotAPIError
import it.unibo.harmonikt.api.dto.RobotActionDTO.MoveToTargetDTO.MirMoveToTargetDTO
import it.unibo.harmonikt.api.dto.RobotActionDTO.MoveToTargetDTO.SpotMoveToTargetDTO
import it.unibo.harmonikt.api.dto.RobotIdDTO
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO
import it.unibo.harmonikt.api.dto.RobotStatusDTO
import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotType.MIR
import it.unibo.harmonikt.model.RobotType.SPOT
import it.unibo.harmonikt.repository.RobotRepository
import it.unibo.harmonikt.resources.Robots

/**
 * Implementation of the RobotAPI interface for the Robot Manager service.
 * This class provides the concrete implementation of the robot management API.
 *
 * @property robotRepository The repository for accessing robot data.
 * @property actionRepository The repository for accessing action data.
 */
class RobotAPIImpl(
    private val robotRepository: RobotRepository,
//    private val actionRepository: ActionRepository,
    private val client: HttpClient,
) : RobotAPI {
    override suspend fun getAllRobots(): Either<RobotAPIError, List<RobotInfo>> =
        Either.Right(robotRepository.getRobots())

    override suspend fun getRobotById(robotId: Robots.Id): Either<RobotAPIError, RobotStatusDTO> = Either.catch {
        robotRepository.getRobotById(robotId.robotId)?.let {
            when (it) {
                MIR -> client.get("http://mir-service/robots/${robotId.robotId}").body<RobotStatusDTO>()
                SPOT -> client.get("http://spot-service/robots/${robotId.robotId}").body<RobotStatusDTO>()
            }
        } ?: return Either.Left(RobotAPIError.RobotNotFound(robotId.robotId))
    }.mapLeft { error -> RobotAPIError.GenericRobotAPIError(error.message) }

    override suspend fun registerNewRobot(request: RobotRegistrationDTO): Either<RobotAPIError, RobotIdDTO> =
        Either.catch {
            when (request) {
                is RobotRegistrationDTO.MirRobotRegistrationDTO -> {
                    val robotId = client.post("http://mir-service/robots") {
                        setBody(request)
                        contentType(ContentType.Application.Json)
                    }.body<RobotIdDTO>()
                    robotRepository.registerRobot(robotId.toRobotId(), MIR, request.canonicalName)
                    robotId
                }
                is RobotRegistrationDTO.SpotRobotRegistrationDTO -> {
                    val robotId = client.post("http://spot-service/robots") {
                        setBody(request)
                        contentType(ContentType.Application.Json)
                    }.body<RobotIdDTO>()
                    robotRepository.registerRobot(robotId.toRobotId(), SPOT, request.canonicalName)
                    robotId
                }
            }
        }.mapLeft { error -> RobotAPIError.RobotCreationFailed(error.message) }

    override suspend fun deleteRobot(robotId: Robots.Id): Either<RobotAPIError, RobotIdDTO> = Either.catch {
        robotRepository.getRobotById(robotId.robotId)?.let {
            when (it) {
                MIR -> client.delete("http://mir-service/robots/${robotId.robotId}")
                SPOT -> client.delete("http://spot-service/robots/${robotId.robotId}")
            }
            robotRepository.deleteRobot(robotId.robotId)
            RobotIdDTO(robotId.robotId)
        } ?: return Either.Left(RobotAPIError.RobotNotFound(robotId.robotId))
    }.mapLeft { error -> RobotAPIError.RobotNotFound(robotId.robotId) }

    override suspend fun createRobotAction(robotId: RobotId, action: Action): Either<RobotAPIError, Action> =
        Either.catch {
            robotRepository.getRobotById(robotId)?.let {
                when (it) {
                    MIR -> client.post("http://mir-service/robots/$robotId/actions") {
                        when (action) {
                            is Action.MoveToTarget -> action.target.associatedMarkers.forEach { marker ->
                                if (marker is Marker.MirMarker) {
                                    setBody(
                                        MirMoveToTargetDTO(
                                            target = marker,
                                        ),
                                    )
                                    contentType(ContentType.Application.Json)
                                }
                            }
                        }
                    }.body<Action>()
                    SPOT -> client.post("http://spot-service/robots/$robotId/actions") {
                        when (action) {
                            is Action.MoveToTarget -> action.target.associatedMarkers.forEach { marker ->
                                if (marker is Marker.SpotMarker) {
                                    setBody(
                                        SpotMoveToTargetDTO(
                                            target = marker,
                                        ),
                                    )
                                    contentType(ContentType.Application.Json)
                                }
                            }
                        }
                    }.body<Action>()
                }
            } ?: return Either.Left(RobotAPIError.RobotNotFound(robotId))
        }.mapLeft { error -> RobotAPIError.ActionFailed(error.message) }
}
