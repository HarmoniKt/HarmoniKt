package it.unibo.harmonikt.services

import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.repository.MirRobotRepository

class MirRobotService(private val robotRepository: MirRobotRepository) : RobotService {

    override suspend fun moveToTarget(robotId: RobotId, marker: Marker): Boolean {
        val robot = robotRepository.getRobotById(robotId)
        robot?.let {
            when(marker) {
                is Marker.MirMarker -> moveTo(robot.name, marker.identifier)
                is Marker.SpotMarker -> return false
            }
            return true
        }
        return false
    }

    companion object {
        suspend fun moveTo(mirUrl: String, targetId: String) {
        }
    }
}
