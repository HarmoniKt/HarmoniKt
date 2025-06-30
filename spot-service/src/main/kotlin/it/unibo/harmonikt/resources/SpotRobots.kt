package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import it.unibo.harmonikt.model.Marker
import kotlin.uuid.Uuid

@Resource("/robots")
class SpotRobots {
    @Resource("{id}")
    class Id(val parent: SpotRobots, val id: Uuid) {
        @Resource("position")
        class Position(val parent: Id)

        @Resource("move")
        class Move(val parent: Id, val target: Marker.SpotMarker)
    }
}
