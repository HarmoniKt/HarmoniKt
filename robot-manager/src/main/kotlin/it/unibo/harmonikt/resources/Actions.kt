package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import kotlin.uuid.Uuid

@Resource("/actions")
class Actions {
    @Resource("{id}")
    class Id(val parent: Actions, val id: Uuid)
}
