package it.unibo.harmonikt.api

import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.BatteryLevel
import it.unibo.harmonikt.model.CanonicalName
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId
import it.unibo.harmonikt.model.RobotPosition
import it.unibo.harmonikt.model.RobotState
import it.unibo.harmonikt.repository.ActionRepository
import it.unibo.harmonikt.repository.RobotRepository
import it.unibo.harmonikt.repositories.RobotRepositoryRobotManager
import kotlin.uuid.Uuid

/**
 * Implementation of the RobotAPI interface for the Robot Manager service.
 * This class provides the concrete implementation of the robot management API.
 *
 * @property robotRepository The repository for accessing robot data.
 * @property actionRepository The repository for accessing action data.
 */
class RobotAPIImpl(
    private val robotRepository: RobotRepository,
    private val actionRepository: ActionRepository,
) : RobotAPI {
    override fun getAllRobots(): List<RobotId> {
        TODO("Not yet implemented")
    }

    override fun getRobotById(robotId: RobotId): Robot? {
        TODO("Not yet implemented")
    }

    override fun createRobot(request: RobotCreationRequest): RobotId {
        TODO("Not yet implemented")
    }

    override fun deleteRobot(robotId: RobotId): Boolean {
        TODO("Not yet implemented")
    }

    override fun createRobotAction(
        robotId: RobotId,
        action: Action
    ): RobotId? {
        TODO("Not yet implemented")
    }

}
