package it.unibo.harmonikt.repositories

import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.repository.ActionRepository

/**
 * A repository for managing actions in the Robot Manager service.
 */
class ActionRepositoryRobotManager : ActionRepository {
    override fun queueAction(robotId: RobotId, action: Action): Boolean {
        TODO("Not yet implemented")
    }
}
