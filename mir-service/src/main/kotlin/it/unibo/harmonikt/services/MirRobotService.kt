package it.unibo.harmonikt.services

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.repository.impl.AbstractMirRobotRepository

class MirRobotService(private val client: HttpClient, private val robotRepository: AbstractMirRobotRepository) : RobotService {

    override suspend fun moveToTarget(robotId: RobotId, markerIdentifier: String): Boolean {
        val robot = robotRepository.getMirRobotById(robotId)
        if (robot != null) {
            client.post("http://${robot.host}/api/v2.0.0/mission_queue") {
                header(HttpHeaders.Authorization, "Basic ${robot.apiToken}")
                header(HttpHeaders.ContentType, "application/json")
                setBody(
                    """{"mission_id": "$markerIdentifier"}"""
                )
            }
            return true
        } else return false
    }
}
