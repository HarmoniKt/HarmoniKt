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

/**
 * Implementation of the PointOfInterestAPI interface for managing points of interest.
 *
 * @property pointOfInterestRepository The repository for accessing point of interest data.
 */
class PointOfInterestAPIImpl(val pointOfInterestRepository: PointOfInterestRepository) : PointOfInterestAPI {
    override suspend fun getAllPointsOfInterest(): Either<GenericPointOfInterestAPIError, List<PointOfInterest>> =
        Either.Right(pointOfInterestRepository.getPointsOfInterest())

    override suspend fun getPointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestDTO> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.poiId)?.let { point ->
            PointOfInterestDTO(
                id = point.id,
                name = point.name,
                latitude = point.latitude,
                longitude = point.longitude,
                associatedMarkers = point.associatedMarkers,
            )
        } ?: return Either.Left(PointOfInterestNotFound(poiId.poiId))
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
        if (created) {
            poiId
        } else {
            return Either.Left(PointOfInterestAPIError.PointOfInterestAlreadyExists(poiId.id))
        }
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun deletePointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestIdDTO> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.poiId)?.let { point ->
            pointOfInterestRepository.deletePointOfInterest(point.id)
            PointOfInterestIdDTO(point.id)
        } ?: return Either.Left(PointOfInterestNotFound(poiId.poiId))
    }.mapLeft { error -> PointOfInterestDeletionFailed(poiId.poiId) }

    override suspend fun getPointOfInterestMarkers(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, List<Marker>> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.poiId)?.let { point ->
            point.associatedMarkers
        } ?: return Either.Left(PointOfInterestNotFound(poiId.poiId))
    }.mapLeft { error -> GenericPointOfInterestAPIError(error.message) }

    override suspend fun getMarkerInfo(poiId: PointOfInterests.Id, markerId: Uuid): Either<MarkerAPIError, Marker> =
        Either.catch {
            pointOfInterestRepository.getMarkerInfo(poiId.poiId, markerId)
                ?: return Either.Left(MarkerNotFound(markerId))
        }.mapLeft { error -> GenericMarkerAPIError(error.message) }

    override suspend fun registerMarker(request: MarkerRegistrationDTO): Either<MarkerAPIError, MarkerIdDTO> =
        Either.catch {
            pointOfInterestRepository.getPointOfInterestById(request.associatedPointOfInterest)?.let { point ->
                val markerId = MarkerIdDTO(Uuid.random())
                val associated = pointOfInterestRepository.associateMarker(
                    point.id,
                    request.toMarker(markerId.toMarkerId()),
                )
                if (associated) markerId else return Either.Left(MarkerAssociationFailed(markerId.id, point.id))
            } ?: return Either.Left(
                GenericMarkerAPIError(PointOfInterestNotFound(request.associatedPointOfInterest).toString()),
            )
        }.mapLeft { error -> GenericMarkerAPIError(error.message) }

    override suspend fun removeMarker(
        poiId: PointOfInterests.Id,
        markerId: Markers.Id,
    ): Either<MarkerAPIError, MarkerIdDTO> = Either.catch {
        pointOfInterestRepository.getPointOfInterestById(poiId.poiId)?.let { point ->
            pointOfInterestRepository.getMarkerInfo(point.id, markerId.markerId)?.let { marker ->
                pointOfInterestRepository.dissociateMarker(poiId.poiId, markerId.markerId)
                MarkerIdDTO(markerId.markerId)
            } ?: return Either.Left(MarkerNotFound(markerId.markerId))
        } ?: return Either.Left(GenericMarkerAPIError(PointOfInterestNotFound(poiId.poiId).toString()))
    }.mapLeft { error -> MarkerDeletionFailed(markerId.markerId) }
}
