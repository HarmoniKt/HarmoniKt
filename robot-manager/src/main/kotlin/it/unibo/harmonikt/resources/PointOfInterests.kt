package it.unibo.harmonikt.resources

import io.ktor.resources.Resource

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
    @Resource("/{id}")
    class Id(val parent: PointOfInterests, val id: String)
}
