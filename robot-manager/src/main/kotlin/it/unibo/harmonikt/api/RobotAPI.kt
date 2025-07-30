package it.unibo.harmonikt.api

import arrow.core.Either
import it.unibo.harmonikt.api.dto.RobotIdDTO
import it.unibo.harmonikt.api.dto.RobotRegistrationDTO
import it.unibo.harmonikt.model.Action
import it.unibo.harmonikt.model.Robot
import it.unibo.harmonikt.model.RobotId

/**
 * Represents errors that can occur in the Robot Management API.
 */
sealed interface RobotAPIError {
    /**
     * Represents an error when a robot with the specified ID is not found.
     *
     * @property robotId The unique identifier of the robot that was not found.
     */
    data class RobotNotFound(val robotId: RobotId) : RobotAPIError

    /**
     * Represents an error when a robot with the specified canonical name already exists.
     *
     * @property name The canonical name of the robot that already exists.
     */
    data class RobotCanonicalNameAlreadyExists(val name: String) : RobotAPIError

    /**
     * Represents an error when a robot with the specified ID already exists.
     *
     * @property robotId The unique identifier of the robot that already exists.
     */
    data class RobotDeletionFailed(val robotId: RobotId) : RobotAPIError

    /**
     * Represents an error when the creation of a robot fails.
     *
     * @property reason A description of the reason why the robot creation failed.
     */
    data class RobotCreationFailed(val reason: String?) : RobotAPIError
}

/**
 * Interface defining the Robot Management API.
 * This interface provides methods for managing robots in the system.
 */
interface RobotAPI {
    /**
     * Retrieves all active robots in the system.
     *
     * @return A list of all active robots Uuids.
     */
    suspend fun getAllRobots(): Either<RobotAPIError, List<RobotId>>

    /**
     * Retrieves information about a specific robot.
     *
     * @param robotId The unique identifier of the robot.
     * @return The robot with the specified ID, or null if not found.
     */
    suspend fun getRobotById(robotId: RobotId): Either<RobotAPIError, Robot>

    /**
     * Creates a new robot in the system based on the provided request.
     *
     * @param request The details required to create the robot, including its name and type.
     * @return The unique identifier of the newly created robot.
     */
    suspend fun registerNewRobot(request: RobotRegistrationDTO): Either<RobotAPIError, RobotIdDTO>

    /**
     * Removes a robot from the system.
     *
     * @param robotId The unique identifier of the robot to remove.
     * @return True if the robot was successfully removed, false otherwise.
     */
    suspend fun deleteRobot(robotId: RobotId): Either<RobotAPIError, Unit>

    /**
     * Creates a new action for a specific robot.
     *
     * @param robotId The unique identifier of the robot.
     * @param action The command to execute.
     * @return The ID of the newly created action, or null if the robot was not found.
     */
    suspend fun createRobotAction(robotId: RobotId, action: Action): Either<RobotAPIError, Action>
}
