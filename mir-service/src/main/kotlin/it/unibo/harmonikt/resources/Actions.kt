package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import it.unibo.harmonikt.model.Action

/**
 * Resource class representing actions that can be performed by robots.
 *
 * This class is used to define the API endpoints for performing actions,
 * such as moving to a target location. The nested Id class allows for
 * specifying a particular action by its identifier.
 */
@Resource("/actions")
class Actions {

    /**
     * Nested resource class for accessing a specific action by its identifier.
     *
     * This class allows for defining endpoints that operate on a specific action,
     * identified by its unique ID.
     *
     * @property parent The parent Actions resource.
     * @property type The specific action type.
     */
    @Resource("/{id}")
    class Id(val parent: Actions, val type: Action)
}
