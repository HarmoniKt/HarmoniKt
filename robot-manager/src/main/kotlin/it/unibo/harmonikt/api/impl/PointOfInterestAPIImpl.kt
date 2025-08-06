package it.unibo.harmonikt.api.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import it.unibo.harmonikt.api.MarkerAPIError
import it.unibo.harmonikt.api.MarkerAPIError.GenericMarkerAPIError
import it.unibo.harmonikt.api.MarkerAPIError.MarkerAssociationFailed
import it.unibo.harmonikt.api.MarkerAPIError.MarkerDeletionFailed
import it.unibo.harmonikt.api.MarkerAPIError.MarkerNotFound
import it.unibo.harmonikt.api.PointOfInterestAPI
import it.unibo.harmonikt.api.PointOfInterestAPIError
import it.unibo.harmonikt.api.PointOfInterestAPIError.GenericPointOfInterestAPIError
import it.unibo.harmonikt.api.PointOfInterestAPIError.PointOfInterestDeletionFailed
import it.unibo.harmonikt.api.PointOfInterestAPIError.PointOfInterestNotFound
import it.unibo.harmonikt.api.dto.MarkerDTO
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.api.dto.MarkerRegistrationDTO
import it.unibo.harmonikt.api.dto.PointOfInterestDTO
import it.unibo.harmonikt.api.dto.PointOfInterestIdDTO
import it.unibo.harmonikt.api.dto.PointOfInterestRegistrationDTO
import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.repository.PointOfInterestRepository
import it.unibo.harmonikt.resources.PointOfInterests
import it.unibo.harmonikt.resources.PointOfInterests.Id.Markers

/**
 * Implementation of the PointOfInterestAPI interface for managing points of interest.
 *
 * @property pointOfInterestRepository The repository for accessing point of interest data.
 * @property client The HTTP client used to make requests to the point of interest service.
 */
class PointOfInterestAPIImpl(val pointOfInterestRepository: PointOfInterestRepository, private val client: HttpClient) :
    PointOfInterestAPI {
    override suspend fun getAllPointsOfInterest(): Either<GenericPointOfInterestAPIError, List<PointOfInterest>> =
        Either.Right(pointOfInterestRepository.getPointsOfInterest())

    override suspend fun getPointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestDTO> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.id)?.let {
            client.get("http://pois/${poiId.id}") {
                contentType(ContentType.Application.Json)
            }.body<PointOfInterestDTO>()
        } ?: return Either.Left(PointOfInterestNotFound(poiId.id))
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun registerPointOfInterest(
        poi: PointOfInterestRegistrationDTO,
    ): Either<PointOfInterestAPIError, PointOfInterestIdDTO> = Either.catch {
        val poiId = client.post("http://pois") {
            setBody(poi)
            contentType(ContentType.Application.Json)
        }.body<PointOfInterestIdDTO>()
        val created = pointOfInterestRepository.registerPointOfInterest(
            id = poiId.toPointOfInterestId(),
            name = poi.canonicalName,
            latitude = poi.latitude,
            longitude = poi.longitude,
        )
        if (created) {
            poiId
        } else {
            return Either.Left(PointOfInterestAPIError.PointOfInterestAlreadyExists(poiId.id))
        }
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun deletePointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestIdDTO> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.id)?.let { point ->
            client.delete("http://pois/${point.id}") {
                contentType(ContentType.Application.Json)
            }.body<PointOfInterestDTO>()
            pointOfInterestRepository.deletePointOfInterest(poiId.id)
            PointOfInterestIdDTO(poiId.id)
        } ?: return Either.Left(PointOfInterestNotFound(poiId.id))
    }.mapLeft { error -> PointOfInterestDeletionFailed(poiId.id) }

    override suspend fun getPointOfInterestMarkers(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, List<MarkerIdDTO>> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.id)?.let { point ->
            client.get("http://pois/${point.id}/markers") {
                contentType(ContentType.Application.Json)
            }.body<List<MarkerIdDTO>>()
        } ?: return Either.Left(PointOfInterestNotFound(poiId.id))
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun getMarkerInfo(
        poiId: PointOfInterests.Id,
        markerId: Markers.Id,
    ): Either<MarkerAPIError, MarkerDTO> = Either.catch {
        pointOfInterestRepository.getMarkerInfo(poiId.id, markerId.id)?.let { marker ->
            client.get("http://pois/${poiId.id}/markers/${markerId.id}") {
                contentType(ContentType.Application.Json)
            }.body<MarkerDTO>()
        } ?: return Either.Left(MarkerNotFound(markerId.id))
    }.mapLeft { error -> GenericMarkerAPIError(error.message) }

    override suspend fun registerMarker(request: MarkerRegistrationDTO): Either<MarkerAPIError, MarkerDTO> =
        Either.catch {
            pointOfInterestRepository.getPointOfInterestById(request.associatedPointOfInterest)?.let { point ->
                val marker = client.post("http://pois/${point.id}/markers") {
                    setBody(request)
                    contentType(ContentType.Application.Json)
                }.body<MarkerDTO>()
                val associated = pointOfInterestRepository.associateMarker(point.id, marker.toMarker())
                if (associated) marker else return Either.Left(MarkerAssociationFailed(marker.id, point.id))
            } ?: return Either.Left(
                GenericMarkerAPIError(PointOfInterestNotFound(request.associatedPointOfInterest).toString()),
            )
        }.mapLeft { error -> GenericMarkerAPIError(error.message) }

    override suspend fun removeMarker(
        poiId: PointOfInterests.Id,
        markerId: Markers.Id,
    ): Either<MarkerAPIError, MarkerIdDTO> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.id)?.let { point ->
            pointOfInterestRepository.getMarkerInfo(point.id, markerId.id)?.let { marker ->
                client.delete("http://pois/${point.id}/markers/${markerId.id}")
                pointOfInterestRepository.dissociateMarker(poiId.id, markerId.id)
                MarkerIdDTO(markerId.id)
            } ?: return Either.Left(MarkerNotFound(markerId.id))
        } ?: return Either.Left(GenericMarkerAPIError(PointOfInterestNotFound(poiId.id).toString()))
    }.mapLeft { error -> MarkerDeletionFailed(markerId.id) }
}
