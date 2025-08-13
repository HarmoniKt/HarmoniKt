package it.unibo.harmonikt.api.impl

import arrow.core.Either
import it.unibo.harmonikt.api.MarkerAPIError
import it.unibo.harmonikt.api.MarkerAPIError.GenericMarkerAPIError
import it.unibo.harmonikt.api.MarkerAPIError.MarkerAssociationFailed
import it.unibo.harmonikt.api.MarkerAPIError.MarkerDeletionFailed
import it.unibo.harmonikt.api.MarkerAPIError.MarkerNotFound
import it.unibo.harmonikt.api.PointOfInterestAPI
import it.unibo.harmonikt.api.PointOfInterestAPIError
import it.unibo.harmonikt.api.PointOfInterestAPIError.GenericPointOfInterestAPIError
import it.unibo.harmonikt.api.PointOfInterestAPIError.PointOfInterestAlreadyExists
import it.unibo.harmonikt.api.PointOfInterestAPIError.PointOfInterestDeletionFailed
import it.unibo.harmonikt.api.PointOfInterestAPIError.PointOfInterestNotFound
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.api.dto.MarkerRegistrationDTO
import it.unibo.harmonikt.api.dto.PointOfInterestDTO
import it.unibo.harmonikt.api.dto.PointOfInterestIdDTO
import it.unibo.harmonikt.api.dto.PointOfInterestRegistrationDTO
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.repository.PointOfInterestRepository
import it.unibo.harmonikt.resources.PointOfInterests
import it.unibo.harmonikt.resources.PointOfInterests.Id.Markers
import kotlin.uuid.Uuid

class PointOfInterestAPIImpl(private val pointOfInterestRepository: PointOfInterestRepository) :
    PointOfInterestAPI {

    override suspend fun getAllPointsOfInterest(): Either<GenericPointOfInterestAPIError, List<PointOfInterest>> =
        Either.Right(pointOfInterestRepository.getPointsOfInterest())

    override suspend fun getPointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestDTO> = Either.catch {
        val point = pointOfInterestRepository.getPointOfInterestById(poiId.poiId)
            ?: return Either.Left(PointOfInterestNotFound(poiId.poiId))
        PointOfInterestDTO(
            id = point.id,
            name = point.name,
            latitude = point.latitude,
            longitude = point.longitude,
            associatedMarkers = point.associatedMarkers,
        )
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun registerPointOfInterest(
        poi: PointOfInterestRegistrationDTO,
    ): Either<PointOfInterestAPIError, PointOfInterestIdDTO> = Either.catch {
        val poiId = PointOfInterestIdDTO(Uuid.random())
        val created = pointOfInterestRepository.registerPointOfInterest(
            id = poiId.toPointOfInterestId(),
            name = poi.name,
            latitude = poi.latitude,
            longitude = poi.longitude,
        )
        if (!created) return Either.Left(PointOfInterestAlreadyExists(poiId.id))
        poiId
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun deletePointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestIdDTO> = Either.catch {
        val point = pointOfInterestRepository.getPointOfInterestById(poiId.poiId)
            ?: return Either.Left(PointOfInterestNotFound(poiId.poiId))
        val deleted = pointOfInterestRepository.deletePointOfInterest(point.id)
        if (!deleted) return Either.Left(PointOfInterestDeletionFailed(poiId.poiId))
        PointOfInterestIdDTO(point.id)
    }.mapLeft { error -> PointOfInterestDeletionFailed(poiId.poiId) }

    override suspend fun getPointOfInterestMarkers(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, List<Marker>> = Either.catch {
        val markers = pointOfInterestRepository.getPointOfInterestById(poiId.poiId)?.associatedMarkers
            ?: return Either.Left(PointOfInterestNotFound(poiId.poiId))
        markers
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun getMarkerInfo(poiId: PointOfInterests.Id, markerId: Uuid): Either<MarkerAPIError, Marker> =
        Either.catch {
            val marker = pointOfInterestRepository.getMarkerInfo(poiId.poiId, markerId)
                ?: return Either.Left(MarkerNotFound(markerId))
            marker
        }.mapLeft { error -> GenericMarkerAPIError(error.message) }

    override suspend fun registerMarker(
        associatedPoi: PointOfInterests.Id,
        registration: MarkerRegistrationDTO,
    ): Either<MarkerAPIError, MarkerIdDTO> = Either.catch {
        val point = pointOfInterestRepository.getPointOfInterestById(associatedPoi.poiId)
            ?: return Either.Left(
                GenericMarkerAPIError(PointOfInterestNotFound(associatedPoi.poiId).toString()),
            )
        val markerId = MarkerIdDTO(Uuid.random())
        val associated = pointOfInterestRepository.associateMarker(point.id, registration.toMarker(markerId))
        if (!associated) return Either.Left(MarkerAssociationFailed(markerId.id, point.id))
        markerId
    }.mapLeft { error -> GenericMarkerAPIError(error.message) }

    override suspend fun removeMarker(
        poiId: PointOfInterests.Id,
        marker: Markers.Id,
    ): Either<MarkerAPIError, MarkerIdDTO> = Either.catch {
        val point = pointOfInterestRepository.getPointOfInterestById(poiId.poiId)
            ?: return Either.Left(GenericMarkerAPIError(PointOfInterestNotFound(poiId.poiId).toString()))
        val markerEntity = pointOfInterestRepository.getMarkerInfo(point.id, marker.markerId)
            ?: return Either.Left(MarkerNotFound(marker.markerId))
        val markerId = when (markerEntity) {
            is Marker.MirMarker -> markerEntity.id
            is Marker.SpotMarker -> markerEntity.id
        }
        pointOfInterestRepository.dissociateMarker(poiId.poiId, markerId)
        MarkerIdDTO(markerId)
    }.mapLeft { error -> MarkerDeletionFailed(marker.markerId) }
}
