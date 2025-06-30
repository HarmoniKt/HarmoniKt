package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import kotlin.uuid.Uuid

/**
 * Represents the Actions resource in the Robot Manager service.
 * This resource is used to manage actions associated with robots.
 */
@Resource("/actions")
class Actions {
    /**
     * Represents a specific action identified by its ID.
     *
     * This nested resource allows for operations on a specific action
     * by its unique identifier.
     * @param parent The parent Actions resource.
     * @param id The unique identifier of the action.
     */
    @Resource("{id}")
    class Id(val parent: Actions, val id: Uuid)
}
