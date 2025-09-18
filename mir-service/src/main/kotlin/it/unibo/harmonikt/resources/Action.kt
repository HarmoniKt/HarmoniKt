package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import it.unibo.harmonikt.model.Action

@Resource("/actions")
class Actions {

    @Resource("/{id}")
    class Id(val parent: Actions, val type: Action)
}
