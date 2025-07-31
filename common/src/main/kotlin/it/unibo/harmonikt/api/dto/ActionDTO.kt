package it.unibo.harmonikt.api.dto

import io.ktor.server.plugins.requestvalidation.ValidationResult
import it.unibo.harmonikt.model.Action
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) for representing an action that can be performed by a robot.
 *
 * This class encapsulates the information needed for a robot to perform an action,
 * such as moving to a target location.
 *
 * @property target The point of interest that the robot should interact with.
 */
@Serializable
data class ActionDTO(val target: PointOfInterestDTO) {
    /**
     * Converts this DTO to a domain model Action.
     *
     * @return An Action domain model representing this DTO.
     */
    fun toAction(): Action {
        // Currently only supports MoveToTarget actions
        return Action.MoveToTarget(
            target = target.toPointOfInterest(),
        )
    }

    /**
     * Companion object for validating action DTOs and converting between DTOs and domain models.
     *
     * This object provides methods to validate the fields of the action DTOs,
     * ensuring that all required fields are properly filled out, and to convert
     * between ActionDTO and Action domain models.
     */
    companion object {
        /**
         * Creates an ActionDTO from an Action domain model.
         *
         * @param action The Action domain model to convert.
         * @return An ActionDTO representing the given Action.
         */
        fun fromAction(action: Action): ActionDTO = when (action) {
            is Action.MoveToTarget -> ActionDTO(
                target = PointOfInterestDTO.fromPointOfInterest(action.target),
            )
        }
    }
}

/**
 * Data Transfer Object (DTO) for registering actions that robots can perform.
 *
 * This sealed interface defines the structure for representing different types of actions
 * in the system, such as moving to a target location. Each action type has its own specific
 * details and parameters.
 */
@Serializable
sealed interface ActionRegistrationDTO {
    /**
     * Data Transfer Object (DTO) for representing a move-to-target action.
     *
     * This class encapsulates the information needed for a robot to navigate to a specific
     * point of interest in the environment.
     *
     * @property target The point of interest that the robot should move to.
     */
    @Serializable
    @SerialName("MoveToTarget")
    data class MoveToTargetDTO(val target: PointOfInterestDTO) : ActionRegistrationDTO {
        /**
         * Converts this DTO to a domain model Action.
         *
         * @return An Action domain model representing this DTO.
         */
        fun toAction(): Action.MoveToTarget = Action.MoveToTarget(
            target = target.toPointOfInterest(),
        )
    }

    /**
     * Companion object for validating and converting ActionRegistrationDTO objects.
     */
    companion object {
        /**
         * Validates the provided action registration DTO.
         *
         * @param dto The ActionRegistrationDTO to validate.
         * @return A ValidationResult indicating whether the DTO is valid.
         */
        fun validate(dto: ActionRegistrationDTO): ValidationResult = when (dto) {
            is MoveToTargetDTO -> {
                when {
                    dto.target.associatedMarkers.isEmpty() ->
                        ValidationResult.Invalid("Target must have at least one associated marker")
                    else -> ValidationResult.Valid
                }
            }
        }

        /**
         * Creates an ActionRegistrationDTO from an Action domain model.
         *
         * @param action The Action domain model to convert.
         * @return An ActionRegistrationDTO representing the given Action.
         */
        fun fromAction(action: Action): ActionRegistrationDTO = when (action) {
            is Action.MoveToTarget -> MoveToTargetDTO(
                target = PointOfInterestDTO.fromPointOfInterest(action.target),
            )
        }
    }
}
