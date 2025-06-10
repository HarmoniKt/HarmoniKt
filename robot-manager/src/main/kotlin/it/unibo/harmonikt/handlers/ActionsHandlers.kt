package it.unibo.harmonikt.handlers

import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import it.unibo.harmonikt.repository.ActionRepository
import it.unibo.harmonikt.resources.Actions

object ActionsHandlers {
    fun Routing.setupActionsHandlers(repository: ActionRepository) {
        get<Actions> { actions ->
            call.respondText("Actions resource accessed: $actions")
        }
        post<Actions> { actions ->
            call.respondText("Actions resource created with POST: $actions")
        }
        get<Actions.Id> { actionId ->
            call.respondText("Action ID resource accessed with ID: ${actionId.id}")
        }
    }
}
