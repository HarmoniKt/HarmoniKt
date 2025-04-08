package it.unibo.harmonikt

sealed interface Action {
    data class ContinuousAction(val temp: Int) : Action

    data class DiscreteAction(val temp: Int) : Action
}
