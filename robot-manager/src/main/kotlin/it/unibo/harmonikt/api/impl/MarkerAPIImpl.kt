package it.unibo.harmonikt.api.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import it.unibo.harmonikt.api.MarkerAPI
import it.unibo.harmonikt.api.MarkerAPIError
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.api.dto.MarkerRegistrationDTO
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.Marker.MirMarker
import it.unibo.harmonikt.model.Marker.SpotMarker
import it.unibo.harmonikt.repository.MarkerRepository
import it.unibo.harmonikt.resources.Markers

class MarkerAPIImpl(private val markerRepository: MarkerRepository<Marker>, private val client: HttpClient) :
    MarkerAPI {
    override suspend fun getAllMarkers(): Either<MarkerAPIError, List<Marker>> =
        Either.Right(markerRepository.getMarkers())

    override suspend fun getMarkerInfo(markerId: Markers.Id): Either<MarkerAPIError, MarkerIdDTO> = Either.catch {
        markerRepository.getMarkerById(markerId.id)?.let { marker ->
            when (marker) {
                is SpotMarker -> client.get("http://spot-service/markers/${marker.id}").body<MarkerIdDTO>()
                is MirMarker -> client.get("http://mir-service/markers/${marker.id}").body<MarkerIdDTO>()
            }
        } ?: return Either.Left(MarkerAPIError.MarkerNotFound(markerId.id))
    }.mapLeft { error -> MarkerAPIError.GenericMarkerAPIError(error.message) }

    override suspend fun registerNewMarker(marker: MarkerRegistrationDTO): Either<MarkerAPIError, MarkerIdDTO> =
        Either.catch {
            when (marker) {
                is MarkerRegistrationDTO.MirMarkerRegistrationDTO -> {
                    val markerId = client.post("http://mir-service/markers") {
                        setBody(marker)
                        contentType(ContentType.Application.Json)
                    }.body<MarkerIdDTO>()
                    markerRepository.createMarker(MirMarker(markerId.toMarkerId(), marker.identifier))
                    markerId
                }
                is MarkerRegistrationDTO.SpotMarkerRegistrationDTO -> {
                    val markerId = client.post("http://spot-service/markers") {
                        setBody(marker)
                        contentType(ContentType.Application.Json)
                    }.body<MarkerIdDTO>()
                    markerRepository.createMarker(SpotMarker(markerId.toMarkerId(), marker.fiducial))
                    markerId
                }
            }
        }.mapLeft { error -> MarkerAPIError.GenericMarkerAPIError(error.message) }

    override suspend fun deleteMarker(markerId: Markers.Id): Either<MarkerAPIError, MarkerIdDTO> = Either.catch {
        markerRepository.getMarkerById(markerId.id)?.let {
            when (it) {
                is SpotMarker -> client.post("http://spot-service/markers/${it.id}") {
                    contentType(ContentType.Application.Json)
                }.body<MarkerIdDTO>()
                is MirMarker -> client.post("http://mir-service/markers/${it.id}") {
                    contentType(ContentType.Application.Json)
                }.body<MarkerIdDTO>()
            }
        } ?: return Either.Left(MarkerAPIError.MarkerNotFound(markerId.id))
    }.mapLeft { error -> MarkerAPIError.MarkerDeletionFailed(markerId.id) }
}
