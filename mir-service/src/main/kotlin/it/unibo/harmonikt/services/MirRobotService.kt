package it.unibo.harmonikt.services

import io.ktor.client.engine.apache.Apache
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotInfo
import it.unibo.harmonikt.repository.MirRobotRepository

import java.security.MessageDigest
import java.util.Base64

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable data class MissionCreate(val name: String, val groupId: String? = null)

@Serializable data class MissionResp(val guid: String, val name: String)

@Serializable data class MissionActionParam(val id: String, val value: JsonElement?)

@Serializable data class MissionActionCreate(
    val actionType: String,
    val parameters: List<MissionActionParam>,
    val priority: Int = 0,
)

@Serializable data class MissionActionResp(val guid: String, val actionType: String)

@Serializable data class QueueReq(val missionId: String)

@Serializable data class QueueResp(val id: Int)

class MirRobotService(private val robotRepository: MirRobotRepository) : RobotService {

    override suspend fun moveToTarget(robotId: RobotId, pointOfInterest: PointOfInterest): Boolean {
        val robot: RobotInfo? = robotRepository.getRobotById(robotId)
        robot?.let {
            pointOfInterest
                .associatedMarkers
                .filterIsInstance<Marker.MirMarker>()
                .firstOrNull()
                ?.let {
                    moveTo(robot.canonicalName, it.identifier, "<username>", "<password>")
                }
            return true
        }
        return false
    }

    companion object {

        suspend fun createMission(client: HttpClient, baseUrl: String, auth: String): MissionResp {
            val body = MissionCreate(name = "MoveTo_Wilab1", groupId = "all")
            val resp: String = client.post("$baseUrl/v2.0.0/missions") {
                header(HttpHeaders.Authorization, "Basic $auth")
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(body))
            }.bodyAsText()
            return Json.decodeFromString(resp)
        }

        fun buildAuth(user: String, password: String): String {
            val sha = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
            val shaHex = sha.joinToString("") { "%02x".format(it) }
            return Base64.getEncoder().encodeToString("$user:$shaHex".toByteArray())
        }

        suspend fun addMoveAction(
            client: HttpClient,
            baseUrl: String,
            auth: String,
            missionGuid: String,
            targetPosGuid: String,
        ): MissionActionResp {
            val payload = MissionActionCreate(
                actionType = "move",
                parameters = listOf(
                    MissionActionParam("position", JsonPrimitive(targetPosGuid)),
                    MissionActionParam("blocked_path_timeout", JsonPrimitive(60)),
                    MissionActionParam("distance_threshold", JsonPrimitive(0.1)),
                ),
                priority = 0,
            )
            val resp: String = client.post("$baseUrl/v2.0.0/missions/$missionGuid/actions") {
                header(HttpHeaders.Authorization, "Basic $auth")
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(payload))
            }.bodyAsText()
            return Json.decodeFromString(resp)
        }

        suspend fun enqueueMission(client: HttpClient, baseUrl: String, auth: String, missionGuid: String): QueueResp {
            val resp: String = client.post("$baseUrl/v2.0.0/mission_queue") {
                header(HttpHeaders.Authorization, "Basic $auth")
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(QueueReq(missionId = missionGuid)))
            }.bodyAsText()
            return Json.decodeFromString(resp)
        }

        suspend fun moveTo(mirUrl: String, targetId: String, username: String, password: String) {
            val auth = buildAuth(username, password)
            HttpClient(Apache) {
                expectSuccess = true
            }.use { client ->
                val mission = createMission(client, mirUrl, auth)
                addMoveAction(client, mirUrl, auth, mission.guid, targetId)
                enqueueMission(client, mirUrl, auth, mission.guid)
            }
        }
    }
}
