package it.unibo.harmonikt.api.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import it.unibo.harmonikt.api.MarkerAPIError
import it.unibo.harmonikt.api.PointOfInterestAPI
import it.unibo.harmonikt.api.PointOfInterestAPIError
import it.unibo.harmonikt.api.dto.MarkerDTO
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.api.dto.PointOfInterestDTO
import it.unibo.harmonikt.api.dto.PointOfInterestRegistrationDTO
import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.repository.PointOfInterestRepository
import it.unibo.harmonikt.resources.PointOfInterests
import it.unibo.harmonikt.resources.PointOfInterests.Id.Markers

class PointOfInterestAPIImpl(val pointOfInterestRepository: PointOfInterestRepository, private val client: HttpClient) : PointOfInterestAPI {
    override suspend fun getAllPointsOfInterest(): Either<PointOfInterestAPIError, List<PointOfInterest>> =
        Either.Right(pointOfInterestRepository.getPointsOfInterest())

    override suspend fun getPointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestDTO> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.id)?.let {
            client.get("http://pois/${poiId.id}") {
                contentType(ContentType.Application.Json)
            }.body<PointOfInterestDTO>()
        } ?: return Either.Left(PointOfInterestAPIError.PointOfInterestNotFound(poiId.id))
    }.mapLeft { error ->
        PointOfInterestAPIError.GenericPointOfInterestAPIError(error.message ?: "Unknown error")
    }

    override suspend fun registerPointOfInterest(poi: PointOfInterestRegistrationDTO): Either<PointOfInterestAPIError, PointOfInterestDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePointOfInterest(poiId: PointOfInterests.Id): Either<PointOfInterestAPIError.PointOfInterestDeletionFailed, PointOfInterestDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getPointOfInterestMarkers(poiId: PointOfInterests.Id): Either<PointOfInterestAPIError, List<Markers.Id>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarkerInfo(
        poiId: PointOfInterests.Id,
        markerId: Markers.Id,
    ): Either<MarkerAPIError.MarkerNotFound, MarkerDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun registerMarker(
        poiId: PointOfInterests.Id,
        markerId: Markers.Id,
    ): Either<MarkerAPIError.MarkerAlreadyExists, MarkerIdDTO> = Either.catch {
        // todo (Angela) before I have to get the marker info, then I can associate it with the POI
        // because the association is done by the Marker data structure
        pointOfInterestRepository.associateMarker(poiId.id, markerId.id)?.let {
            client.get("http://pois/${poiId.id}/markers/${markerId.id}") {
                contentType(ContentType.Application.Json)
            }.body<MarkerIdDTO>()
        } ?: return Either.Left(MarkerAPIError.MarkerAlreadyExists(markerId.id))
    }.mapLeft { error -> MarkerAPIError.GenericMarkerAPIError(error.message ?: "Unknown error") }

    override suspend fun removeMarker(
        poiId: PointOfInterests.Id,
        markerId: Markers.Id,
    ): Either<MarkerAPIError.MarkerDeletionFailed, MarkerIdDTO> {
        TODO("Not yet implemented")
    }

}
