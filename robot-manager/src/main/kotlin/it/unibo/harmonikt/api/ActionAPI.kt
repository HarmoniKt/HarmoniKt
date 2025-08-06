@file:Suppress("MatchingDeclarationName")

package it.unibo.harmonikt.api

import arrow.core.Either
import it.unibo.harmonikt.model.Action
import kotlinx.serialization.Serializable

/**
 * Sealed interface representing errors that can occur in the Action API.
 * This interface defines various error types that can be returned by the API methods,
 * such as generic errors and specific action failures.
 */
@Serializable
sealed interface ActionAPIError {
    /**
     * Represents a generic error in the Action API.
     *
     * @property message An optional message describing the error.
     */
    @Serializable
    data class GenericActionAPIError(val message: String? = null) : ActionAPIError

    /**
     * Represents an error when an action cannot be performed on a robot.
     *
     * @property reason A description of the reason why the action could not be performed.
     */
    @Serializable
    data class ActionFailed(val reason: String?) : ActionAPIError
}

/**
 * Interface defining the Action API for managing actions associated with robots.
 * This API provides methods to create and retrieve actions for robots.
 */
interface ActionAPI {
    /**
     * Creates a new action for a robot.
     *
     * @param robotId The unique identifier of the robot.
     * @param action The action to be created.
     * @return A result containing the created action or an error if the creation failed.
     */
    suspend fun createRobotAction(robotId: String, action: Action): Either<ActionAPIError, Action>

    /**
     * Retrieves all actions associated with a robot.
     *
     * @param robotId The unique identifier of the robot.
     * @return A result containing a list of actions or an error if retrieval failed.
     */
    suspend fun getRobotActions(robotId: String): Either<ActionAPIError, List<Action>>
}
