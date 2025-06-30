package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import kotlin.uuid.Uuid

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
         */
        @Resource("position")
        class Position(val parent: Id, val description: String)
    }
}
