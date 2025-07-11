package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import kotlin.uuid.Uuid

/**
 * Represents the SpotMarkers resource in the Spot service.
 *
 * This resource is used to manage markers within the Spot service,
 * allowing for operations such as retrieving, creating, and managing
 * specific markers identified by their unique IDs.
 */
@Resource("/markers")
class SpotMarkers {
    /**
     * Represents a specific marker identified by its ID.
     *
     * This nested resource allows for operations on a specific marker
     * by its unique identifier.
     * @param parent The parent SpotMarkers resource.
     * @param id The unique identifier of the marker.
     */
    @Resource("{id}")
    class Id(val parent: SpotMarkers, val id: Uuid) {
        /**
         * Represents the position of a specific marker.
         *
         * This nested resource allows for operations related to the position
         * of a specific marker identified by its ID.
         * @param parent The parent Id resource of SpotMarkers.
         * @param description A description of the marker's position.
         */
        @Resource("position")
        class Position(val parent: Id, val description: String)
    }
}
