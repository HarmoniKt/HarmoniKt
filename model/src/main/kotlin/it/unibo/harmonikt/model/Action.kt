package it.unibo.harmonikt.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Action {
//    data class ContinuousAction(val temp: Int) : Action
//
//    data class DiscreteAction(val temp: Int) : Action

    data class MoveToTarget(val target: PointOfInterest) : Action
}
