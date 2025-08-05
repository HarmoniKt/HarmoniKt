package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import kotlin.uuid.Uuid

/**
 * Resource representing the collection of points of interest (POI).
 *
 * Endpoint: `/pois`
 */
@Resource("/pois")
class PointOfInterests {
    /**
     * Resource representing a single point of interest identified by `id`.
     *
     * Endpoint: `/pois/{id}`
     * @property parent reference to the parent PointOfInterests resource
     * @property id identifier of the point of interest
     */
    @Resource("/{poiId}")
    class Id(val parent: PointOfInterests, val id: Uuid) {

        /**
         * Represents the markers associated with a specific point of interest.
         *
         * This nested resource allows for operations on markers related to a specific point of interest.
         * @param parent The parent PointOfInterests.Id resource.
         */
        @Resource("/markers")
        class Markers(val parent: PointOfInterests.Id) {
            /**
             * Represents a specific marker identified by its ID.
             *
             * This nested resource allows for operations on a specific marker
             * by its unique identifier.
             * @param parent The parent Marker resource.
             * @param id The unique identifier of the marker.
             */
            @Resource("/{markerId}")
            class Id(val parent: Markers, val id: Uuid) {
                /**
                 * Represents the position of a specific marker.
                 *
                 * This nested resource allows for operations on the position
                 * of a specific marker by its unique identifier.
                 * @param parent The parent Id resource.
                 */
                @Resource("/position")
                class Position(val parent: Id)
            }
        }
    }
}
