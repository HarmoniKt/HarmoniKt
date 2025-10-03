package it.unibo.harmonikt.services

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.repository.impl.AbstractMirRobotRepository

/**
 * Service class for managing Mir robots and their actions.
 *
 * This class provides methods to interact with Mir robots, such as moving them to target locations.
 * It uses an HTTP client to communicate with the robots' APIs and a repository to manage robot data.
 *
 * @property client The HTTP client used for making requests to the robots.
 * @property robotRepository The repository for managing Mir robot data.
 */
class MirRobotService(private val client: HttpClient, private val robotRepository: AbstractMirRobotRepository) :
    RobotService {

    override suspend fun moveToTarget(robotId: RobotId, markerIdentifier: String): Boolean {
        val robot = robotRepository.getMirRobotById(robotId)
        if (robot != null) {
            val request = client.post("http://${robot.host}/api/v2.0.0/mission_queue") {
                header(HttpHeaders.Authorization, "Basic ${robot.apiToken}")
                header(HttpHeaders.ContentType, "application/json")
                setBody(
                    """{"mission_id": "$markerIdentifier"}""",
                )
            }
            return request.status.value in HttpStatusCode.OK.value..HttpStatusCode.PartialContent.value
        } else {
            return false
        }
    }
}
