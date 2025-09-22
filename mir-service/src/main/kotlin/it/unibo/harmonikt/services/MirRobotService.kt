package it.unibo.harmonikt.services

import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.repository.MirRobotRepository
import kotlin.uuid.Uuid

class MirRobotService(private val robotRepository: MirRobotRepository) : RobotService {

    override suspend fun moveToTarget(robotId: RobotId, marker: Uuid): Boolean {
        val robot = robotRepository.getRobotById(robotId)
        robot?.let {
            println("Moving robot ${robot.name} to marker $marker")
//            when(marker) {
//                is Marker.MirMarker -> print("lessgo")//moveTo(robot.name, marker.identifier, "<username>", "<password>")
//                is Marker.SpotMarker -> return false
//            }
            return true
        }
        return false
    }

    companion object {
        suspend fun moveTo(mirUrl: String, targetId: String) {
        }
    }
}
