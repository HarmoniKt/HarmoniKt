package it.unibo.harmonikt

import it.unibo.harmonikt.model.Robot

object MirRegistry {

    private var robots =  listOf<Robot>()

    val availableRobots: List<Robot>
        get() = robots

    fun registerRobot(robot: Robot) {
        robots = robots + robot
    }

    fun unregisterRobot(robot: Robot) {
        robots = robots - robot
    }

    fun clear(){
        robots = listOf()
    }

}

