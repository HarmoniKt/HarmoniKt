package it.unibo.harmonikt.api

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import it.unibo.harmonikt.api.dto.RobotIdDTO
import it.unibo.harmonikt.api.dto.RobotInfoDTO
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO
import it.unibo.harmonikt.api.dto.RobotStatusDTO
import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.repository.ActionRepository
import it.unibo.harmonikt.repository.RobotRepository
import it.unibo.harmonikt.resources.Robots
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Implementation of the RobotAPI interface for the Robot Manager service.
 * This class provides the concrete implementation of the robot management API.
 *
 * @property robotRepository The repository for accessing robot data.
 * @property actionRepository The repository for accessing action data.
 */
class RobotAPIImpl(
    private val robotRepository: RobotRepository,
    private val actionRepository: ActionRepository,
    private val client: HttpClient,
) : RobotAPI {
    override suspend fun getAllRobots(): Either<RobotAPIError, List<RobotInfo>> =
        Either.Right(robotRepository.getRobots())

    override suspend fun getRobotById(robotId: Robots.Id): Either<RobotAPIError, RobotStatusDTO> = Either.catch {
        robotRepository.getRobotById(robotId.robotId)?.let {
            when (it) {
                RobotType.MIR -> client.get("http://mir-service/robots/${robotId.robotId}").body<RobotStatusDTO>()
                RobotType.SPOT -> client.get("http://spot-service/robots/${robotId.robotId}").body<RobotStatusDTO>()
            }
        } ?: return Either.Left(RobotAPIError.RobotNotFound(robotId.robotId))
    }.mapLeft { error -> RobotAPIError.RobotNotFound(robotId.robotId) }

    override suspend fun registerNewRobot(request: RobotRegistrationDTO): Either<RobotAPIError, RobotIdDTO> =
        Either.catch {
            when (request) {
                is RobotRegistrationDTO.MirRobotRegistrationDTO -> {
                    val robotId = client.post("http://mir-service/robots") {
                        setBody(request)
                        contentType(ContentType.Application.Json)
                    }.body<RobotIdDTO>()
                    robotRepository.registerRobot(robotId.toRobotId(), RobotType.MIR, request.canonicalName)
                    robotId
                }
                is RobotRegistrationDTO.SpotRobotRegistrationDTO -> {
                    val robotId = client.post("http://spot-service/robots") {
                        setBody(request)
                        contentType(ContentType.Application.Json)
                    }.body<RobotIdDTO>()
                    robotRepository.registerRobot(robotId.toRobotId(), RobotType.SPOT, request.canonicalName)
                    robotId
                }
            }
        }.mapLeft { error -> RobotAPIError.RobotCreationFailed(error.message) }

    override suspend fun deleteRobot(robotId: Robots.Id): Either<RobotAPIError, RobotIdDTO> = Either.catch {
        robotRepository.getRobotById(robotId.robotId)?.let {
            when (it) {
                RobotType.MIR -> client.delete("http://mir-service/robots/${robotId.robotId}")
                RobotType.SPOT -> client.delete("http://spot-service/robots/${robotId.robotId}")
            }
            robotRepository.deleteRobot(robotId.robotId)
            RobotIdDTO(robotId.robotId)
        } ?: return Either.Left(RobotAPIError.RobotNotFound(robotId.robotId))
    }.mapLeft { error -> RobotAPIError.RobotNotFound(robotId.robotId) }

    override suspend fun createRobotAction(robotId: RobotId, action: Action): Either<RobotAPIError, Action> {
        TODO("Not yet implemented")
    }
}
