package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.PointOfInterest
import kotlin.uuid.Uuid

/**
 * Represents the MirRobots resource in the Mir service.
 *
 * This resource is used to manage robots within the Mir service,
 * allowing for operations such as retrieving, creating, and managing
 * specific robots identified by their unique IDs.
 */
@Resource("/robots")
class MirRobots {
    /**
     * Represents a specific robot identified by its ID.
     *
     * This nested resource allows for operations on a specific robot
     * by its unique identifier.
     * @param parent The parent MirRobots resource.
     * @param id The unique identifier of the robot.
     */
    @Resource("{id}")
    class Id(val parent: MirRobots, val id: Uuid) {
        /**
         * Represents the position of a specific robot.
         *
         * This nested resource allows for operations related to the position
         * of a specific robot identified by its ID.
         * @param parent The parent Id resource of MirRobots.
         */
        @Resource("position")
        class Position(val parent: Id)

        /**
         * Represents the action of moving a robot to a specific target marker.
         *
         * This nested resource allows for moving a robot to a target marker
         * identified by its unique MirMarker.
         *
         * @param parent The parent Id resource of MirRobots.
         * @param target The target marker where the robot should be moved.
         */
        @Resource("move")
        class Move(val parent: Id, val target: PointOfInterest)
    }
}
