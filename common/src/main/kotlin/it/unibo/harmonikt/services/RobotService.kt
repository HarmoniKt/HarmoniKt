package it.unibo.harmonikt.services

import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.model.RobotId

/**
 * Service interface for interacting with a specific robot.
 */
interface RobotService {

    /**
     * Moves the robot to a target position.
     * @return True if the action was successful, False otherwise.
     */
    fun moveToTarget(robotId: RobotId, pointOfInterest: PointOfInterest): Boolean
}
