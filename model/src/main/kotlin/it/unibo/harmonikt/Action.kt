package it.unibo.harmonikt

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
sealed interface Marker {
    data class MirMarker(val id: Uuid, val identifier: String)
    data class SpotMarker(val id: String, val waypoint: String)
}

@JvmInline
value class Target(val name: String)

sealed interface Action {
    data class ContinuousAction(val temp: Int) : Action

    data class DiscreteAction(val temp: Int) : Action

    data class MoveToTarget(val target: Marker) : Action
}
