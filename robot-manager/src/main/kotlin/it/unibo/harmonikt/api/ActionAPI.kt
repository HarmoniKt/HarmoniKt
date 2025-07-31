package it.unibo.harmonikt.api

import kotlinx.serialization.Serializable

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
