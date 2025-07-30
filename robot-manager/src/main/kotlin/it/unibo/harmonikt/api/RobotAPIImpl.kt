package it.unibo.harmonikt.api

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import it.unibo.harmonikt.api.dto.RobotIdDTO
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO
import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.repository.ActionRepository
import it.unibo.harmonikt.repository.RobotRepository

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
    override suspend fun getAllRobots(): Either<RobotAPIError, List<RobotId>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRobotById(robotId: RobotId): Either<RobotAPIError, Robot> {
        TODO("Not yet implemented")
    }

    override suspend fun registerNewRobot(request: RobotRegistrationDTO): Either<RobotAPIError, RobotIdDTO> =
        Either.catch {
            when (request) {
                is RobotRegistrationDTO.MirRobotRegistrationDTO -> {
                    val robotId = client.post("http://mir-service/robots") { setBody(request) }.body<RobotIdDTO>()
                    robotRepository.registerRobot(robotId.toRobotId(), RobotType.MIR)
                    robotId
                }
                is RobotRegistrationDTO.SpotRobotRegistrationDTO -> {
                    val robotId = client.post("http://spot-service/robots") { setBody(request) }.body<RobotIdDTO>()
                    robotRepository.registerRobot(robotId.toRobotId(), RobotType.SPOT)
                    robotId
                }
            }
        }.mapLeft { error -> RobotAPIError.RobotCreationFailed(error.message) }

    override suspend fun deleteRobot(robotId: RobotId): Either<RobotAPIError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun createRobotAction(robotId: RobotId, action: Action): Either<RobotAPIError, Action> {
        TODO("Not yet implemented")
    }
}
