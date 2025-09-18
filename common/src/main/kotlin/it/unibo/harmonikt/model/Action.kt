package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable

/**
 * Represents an action that can be performed by a robot.
 * This is a sealed interface that defines various types of actions available in the system.
 */
@Serializable
sealed interface Action {
    /**
     * Represents an action to move a robot to a specific target location.
     *
     * @property target The point of interest that the robot should move to.
     */
    data class MoveToTarget(
        /**
         * The destination point that the robot should navigate to.
         */
        val target: PointOfInterest,
    ) : Action
}
