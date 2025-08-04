package it.unibo.harmonikt.api.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import it.unibo.harmonikt.api.PointOfInterestAPI
import it.unibo.harmonikt.api.PointOfInterestAPIError
import it.unibo.harmonikt.api.dto.PointOfInterestDTO
import it.unibo.harmonikt.model.PointOfInterest
import it.unibo.harmonikt.repository.PointOfInterestRepository
import it.unibo.harmonikt.resources.PointOfInterests

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

    override suspend fun registerPointOfInterest(
        poi: PointOfInterestDTO,
    ): Either<PointOfInterestAPIError, PointOfInterestDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePointOfInterest(
        poiId: PointOfInterests.Id,
    ): Either<PointOfInterestAPIError, PointOfInterestDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun registerMarker(
        poiId: PointOfInterests.Id,
        markerId: PointOfInterests.Id
    ): Either<PointOfInterestAPIError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun removeMarker(
        poiId: PointOfInterests.Id,
        markerId: PointOfInterests.Id
    ): Either<PointOfInterestAPIError, Unit> {
        TODO("Not yet implemented")
    }
}
