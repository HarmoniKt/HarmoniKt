package it.unibo.harmonikt.resources

import io.ktor.resources.Resource
import it.unibo.harmonikt.model.RobotId

/**
 * Represents the Robots resource in the Robot Manager service.
 * This resource is used to manage robots in the system.
 */
@Resource("/robots")
class Robots {
    /**
     * Represents a specific robot identified by its ID.
     *
     * This nested resource allows for operations on a specific robot
     * by its unique identifier.
     * @param parent The parent Robots resource.
     * @param robotId The unique identifier of the robot.
     */
    @Resource("{robotId}")
    class Id(val parent: Robots, val robotId: RobotId) {
        /**
         * Represents the actions resource for a specific robot.
         *
         * This nested resource allows for operations on actions
         * associated with a specific robot.
         * @param parent The parent Id resource.
         */
        @Resource("actions")
        class Actions(val parent: Id)
    }
}
