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
import it.unibo.harmonikt.api.PointOfInterestAPIError.PointOfInterestNotFound
import it.unibo.harmonikt.api.RobotAPI
import it.unibo.harmonikt.api.RobotAPIError
import it.unibo.harmonikt.api.RobotAPIError.GenericRobotAPIError
import it.unibo.harmonikt.api.dto.MirMoveToMarkerDTO
import it.unibo.harmonikt.api.dto.MoveToTargetDTO.MirMoveToPOIDTO
import it.unibo.harmonikt.api.dto.MoveToTargetDTO.SpotMoveToTargetDTO
import it.unibo.harmonikt.api.dto.RobotActionDTO
import it.unibo.harmonikt.api.dto.RobotIdDTO
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO
import it.unibo.harmonikt.api.dto.RobotStatusDTO
import it.unibo.harmonikt.api.dto.SpotMoveToFiducialDTO
import it.unibo.harmonikt.model.Marker.MirMarker
import it.unibo.harmonikt.model.Marker.SpotMarker
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotType.MIR
import it.unibo.harmonikt.model.RobotType.SPOT
import it.unibo.harmonikt.repositories.PointOfInterestRepositoryRobotManager
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
    private val poisRepository: PointOfInterestRepositoryRobotManager,
//    private val actionRepository: ActionRepository,
    private val client: HttpClient,
) : RobotAPI {
    override suspend fun getAllRobots(): Either<RobotAPIError, List<RobotInfo>> =
        Either.Right(robotRepository.getRobots())

    override suspend fun getRobotById(robotId: Robots.Id): Either<RobotAPIError, RobotStatusDTO> = Either.catch {
        robotRepository.getRobotById(robotId.robotId)?.let {
            when (it.type) {
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
            when (it.type) {
                MIR -> client.delete("http://mir-service/robots/${robotId.robotId}")
                SPOT -> client.delete("http://spot-service/robots/${robotId.robotId}")
            }
            robotRepository.deleteRobot(robotId.robotId)
            RobotIdDTO(robotId.robotId)
        } ?: return Either.Left(RobotAPIError.RobotNotFound(robotId.robotId))
    }.mapLeft { error -> RobotAPIError.RobotNotFound(robotId.robotId) }

    override suspend fun createRobotAction(robotId: RobotId, action: RobotActionDTO): Either<RobotAPIError, Boolean> =
        Either.catch {
            robotRepository.getRobotById(robotId)?.let {
                when (it.type) {
                    MIR -> client.post("http://mir-service/robots/$robotId/move") {
                        when (action) {
                            is MirMoveToPOIDTO -> {
                                val poi = poisRepository.getPointOfInterestById(action.targetPOI)
                                    ?: return Either.Left(
                                        GenericRobotAPIError(PointOfInterestNotFound(action.targetPOI).toString()),
                                    )
                                val mirMarker = poi.associatedMarkers.first { it is MirMarker } as MirMarker
                                contentType(ContentType.Application.Json)
                                setBody(
                                    MirMoveToMarkerDTO(
                                        identifier = mirMarker.identifier,
                                    ),
                                )
                            }
                            else -> GenericRobotAPIError("Unsupported action type for MIR robot")
                        }
                    }.body<Boolean>()
                    SPOT -> client.post("http://spot-service/robots/$robotId/actions") {
                        when (action) {
                            is SpotMoveToTargetDTO -> {
                                val poi = poisRepository.getPointOfInterestById(action.targetPOI)
                                    ?: return Either.Left(
                                        GenericRobotAPIError(PointOfInterestNotFound(action.targetPOI).toString()),
                                    )
                                val spotMarker = poi.associatedMarkers.first { it is SpotMarker } as SpotMarker
                                contentType(ContentType.Application.Json)
                                setBody(
                                    SpotMoveToFiducialDTO(
                                        fiducial = spotMarker.fiducial,
                                    ),
                                )
                            }
                            else -> GenericRobotAPIError("Unsupported action type for SPOT robot")
                        }
                    }.body<Boolean>()
                }
            } ?: return Either.Left(RobotAPIError.RobotNotFound(robotId))
        }.mapLeft { error -> RobotAPIError.ActionFailed(error.message) }
}
