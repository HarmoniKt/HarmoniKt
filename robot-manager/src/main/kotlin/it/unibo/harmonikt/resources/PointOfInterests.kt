package it.unibo.harmonikt.resources

import io.ktor.resources.Resource

@Resource("/pois")
class PointOfInterests {
    @Resource("{id}")
    class Id(val parent: PointOfInterests, val id: String)
}
