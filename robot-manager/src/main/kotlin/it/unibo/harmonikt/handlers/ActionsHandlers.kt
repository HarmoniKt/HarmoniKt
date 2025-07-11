package it.unibo.harmonikt.handlers

import io.ktor.client.HttpClient
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import it.unibo.harmonikt.repository.ActionRepository
import it.unibo.harmonikt.resources.Actions

/**
 * Handles HTTP requests related to actions in the Robot Manager service.
 */
object ActionsHandlers {
    /**
     * Sets up the routing for actions-related endpoints.
     *
     * @param repository The ActionRepository instance to handle actions data.
     */
    fun Routing.setupActionsHandlers(repository: ActionRepository, client: HttpClient) {
        get<Actions> { actions ->
            repository.equals(10)
            client.equals(10)
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
