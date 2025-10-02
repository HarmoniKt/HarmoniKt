package it.unibo.harmonikt.services

import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.RobotId
import kotlin.uuid.Uuid

/**
 * Service interface for interacting with a specific robot.
 */
interface RobotService {

    /**
     * Moves the robot to a target position.
     * @return True if the action was successful, False otherwise.
     */
    suspend fun moveToTarget(robotId: RobotId, markerIdentifier: String): Boolean
}
