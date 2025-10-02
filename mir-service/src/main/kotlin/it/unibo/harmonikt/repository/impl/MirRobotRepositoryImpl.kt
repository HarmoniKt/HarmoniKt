package it.unibo.harmonikt.repository.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.headers
import it.unibo.harmonikt.model.MirInfo
import it.unibo.harmonikt.model.MirRobot
import it.unibo.harmonikt.model.MirStatusDTO
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.model.toDomain
import it.unibo.harmonikt.repository.MirRobotRepository
import kotlin.uuid.Uuid

/**
 * Implementation of MirRobotRepository using an in-memory list and an HTTP client.
 * @property client The HTTP client used for network operations.
 */
class MirRobotRepositoryImpl(private val client: HttpClient) : AbstractMirRobotRepository() {
    override suspend fun getRobotById(id: RobotId): Robot? {
        val mirRobot = robots.find { it.id == id } ?: return null
        val mir: MirInfo = client.get("https://${mirRobot.host}/status") {
            headers { append("Authorization", "Bearer ${mirRobot.apiToken}") }
        }.body<MirStatusDTO>().toDomain()
        return Robot(
            id = mirRobot.id,
            name = mirRobot.name,
            batteryLevel = mir.batteryLevel,
            currentPosition = RobotPosition(mir.currentPosition.x, mir.currentPosition.y),
            currentState = mir.state,
            type = RobotType.MIR,
        )
    }
}

/**
 * Abstract base class for a repository managing MirRobot entities.
 * Provides common functionalities such as creating, deleting, and listing robots,
 * while leaving robot-specific retrieval logic to implementations.
 *
 * This class serves as a foundational template for repositories that handle MirRobot objects.
 * It extends the MirRobotRepository interface, implementing its general methods
 * using an in-memory collection (`robots`).
 */
abstract class AbstractMirRobotRepository : MirRobotRepository {
    protected val robots: MutableList<MirRobot> = mutableListOf()

    override suspend fun createRobot(canonicalName: String, apiToken: String, host: String): RobotId? {
        val robotId = Uuid.random()
        val newRobot = MirRobot(
            id = robotId,
            name = canonicalName,
            apiToken = apiToken,
            host = host,
        )
        return if (robots.add(newRobot)) robotId else null
    }

    override fun getMirRobotById(id: RobotId): MirRobot? = robots.find { it.id == id }

    override suspend fun deleteRobot(robot: RobotId): Boolean =
        robots.find { it.id == robot }?.let { robots.remove(it) } ?: false

    override suspend fun getRobots(): List<RobotInfo> = robots.map { mir ->
        RobotInfo(
            id = mir.id,
            canonicalName = mir.name,
            type = RobotType.MIR,
        )
    }

    abstract override suspend fun getRobotById(id: RobotId): Robot?
}
