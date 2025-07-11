package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import it.unibo.harmonikt.model.Marker
import kotlin.uuid.Uuid

/**
 * Represents the SpotRobots resource in the Spot service.
 *
 * This resource is used to manage robots within the Spot service,
 * allowing for operations such as retrieving, moving, and managing
 * specific robots identified by their unique IDs.
 */
@Resource("/robots")
class SpotRobots {
    /**
     * Represents a specific robot identified by its ID.
     *
     * This nested resource allows for operations on a specific robot
     * by its unique identifier.
     * @param parent The parent SpotRobots resource.
     * @param id The unique identifier of the robot.
     */
    @Resource("{id}")
    class Id(val parent: SpotRobots, val id: Uuid) {
        /**
         * Represents the position of a specific robot.
         *
         * This nested resource allows for operations related to the position
         * of a specific robot identified by its ID.
         *
         * @param parent The parent Id resource of SpotRobots.
         */
        @Resource("position")
        class Position(val parent: Id)

        /**
         * Represents the action of moving a robot to a specific target marker.
         *
         * This nested resource allows for moving a robot to a target marker
         * identified by its unique SpotMarker.
         *
         * @param parent The parent Id resource of SpotRobots.
         * @param target The target marker where the robot should be moved.
         */
        @Resource("move")
        class Move(val parent: Id, val target: Marker.SpotMarker)
    }
}
