package it.unibo.harmonikt.api.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import it.unibo.harmonikt.api.MarkerAPI
import it.unibo.harmonikt.api.MarkerAPIError
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.repository.MarkerRepository
import it.unibo.harmonikt.resources.Markers
import it.unibo.harmonikt.resources.PointOfInterests

class MarkerAPIImpl(private val markerRepository: MarkerRepository<Marker>, private val client: HttpClient) :
    MarkerAPI {
    override suspend fun getAllMarkers(): Either<MarkerAPIError, List<Marker>> =
        Either.Right(markerRepository.getMarkers())

    override suspend fun getMarkerInfo(poiId: PointOfInterests.Id, markerId: Markers.Id): Either<MarkerAPIError, MarkerIdDTO> = Either.catch {
        markerRepository.getMarkerById(markerId.id)?.let {
            client.get("http://pois/{poiId}/markers/${markerId}") {
                contentType(ContentType.Application.Json)
            }.body<MarkerIdDTO>()
        } ?: return Either.Left(MarkerAPIError.MarkerNotFound(markerId.id))
    }.mapLeft { error -> MarkerAPIError.GenericMarkerAPIError(error.message) }
}
