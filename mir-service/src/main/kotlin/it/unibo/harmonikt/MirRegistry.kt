package it.unibo.harmonikt

import it.unibo.harmonikt.model.Robot
import kotlin.uuid.Uuid

object MirRegistry {

    private var robots =  listOf<Robot>()

    val registeredRobots: List<Robot>
        get() = robots

    fun registerRobot(robot: Robot) {
        robots = robots + robot
    }

    fun unregisterRobot(id: Uuid) {
        robots = robots.filter { it.id != id }
    }

    fun clear(){
        robots = listOf()
    }

}

