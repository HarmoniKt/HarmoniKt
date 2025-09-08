@file:Suppress("MagicNumber", "UnusedPrivateMember")

package it.unibo.harmonikt.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.headers
import it.unibo.harmonikt.MirRobot
import it.unibo.harmonikt.MirStatus
import it.unibo.harmonikt.MirStatusDTO
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotType
import it.unibo.harmonikt.toDomain
import kotlin.uuid.Uuid

/**
 * Repository interface for managing MirRobot entities.
 * This interface extends the generic RobotRepository to handle Mir-specific robots.
 */
interface MirRobotRepository {
    /**
     * Creates a new Mir robot with the given canonical name, API token, and host.
     * @param canonicalName The canonical name of the robot.
     * @param apiToken The API token for the robot.
     * @param host The host address of the robot.
     * @return The unique identifier of the created robot.
     */
    suspend fun createRobot(canonicalName: String, apiToken: String, host: String): RobotId

    /**
     * Deletes the specified Mir robot.
     * @param robot The unique identifier of the robot to delete.
     * @return True if the robot was deleted, false otherwise.
     */
    suspend fun deleteRobot(robot: RobotId): Boolean

    /**
     * Retrieves a list of all Mir robots.
     * @return A list of robot information objects.
     */
    suspend fun getRobots(): List<RobotInfo>

    /**
     * Retrieves a Mir robot by its unique identifier.
     * @param id The unique identifier of the robot.
     * @return The robot object if found, or null otherwise.
     */
    suspend fun getRobotById(id: RobotId): Robot?
}

/**
 * Fake implementation of MirRobotRepository for testing purposes.
 * Stores robots in memory.
 */

/**
 * Implementation of MirRobotRepository using an in-memory list and an HTTP client.
 * @property client The HTTP client used for network operations.
 */
class MirRobotRepositoryImpl(
    /**
     * The HTTP client used for network operations with Mir robots.
     */
    val client: HttpClient,
) : MirRobotRepository {
    private val robots: MutableList<MirRobot> = mutableListOf<MirRobot>()

    override suspend fun createRobot(canonicalName: String, apiToken: String, host: String): RobotId {
        val robotId = Uuid.random()
        val newRobot = MirRobot(
            id = robotId,
            name = canonicalName,
            apiToken = apiToken,
            host = host,
        )
        robots.add(newRobot)
        return robotId
    }

    override suspend fun deleteRobot(robot: RobotId): Boolean = robots.find { it.id == robot }.let { r ->
        return if (r != null) robots.remove(r) else false
    }

    override suspend fun getRobots(): List<RobotInfo> = robots.map { mir ->
        RobotInfo(
            id = mir.id,
            canonicalName = mir.name,
            type = RobotType.MIR,
        )
    }

    override suspend fun getRobotById(id: RobotId): Robot? {
        val mirRobot = robots.find { it.id == id }
        return when {
            mirRobot != null -> {
                val mir: MirStatus = client.get("https://${mirRobot.host}/status") {
                    headers { append("Authorization", "Bearer ${mirRobot.apiToken}") }
                }.body<MirStatusDTO>().toDomain()
                Robot(
                    id = mirRobot.id,
                    name = mirRobot.name,
                    batteryLevel = mir.batteryPercentage,
                    currentPosition = RobotPosition(mir.position.x, mir.position.y),
                    currentState = mir.state,
                    type = RobotType.MIR,
                )
            }
            else -> return null
        }
    }
}
