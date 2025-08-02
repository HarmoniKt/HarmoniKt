package it.unibo.harmonikt.api.impl

import arrow.core.Either
import it.unibo.harmonikt.api.PointOfInterestAPI
import it.unibo.harmonikt.api.PointOfInterestAPIError
import it.unibo.harmonikt.model.PointOfInterest

class PointOfInterestAPIImpl : PointOfInterestAPI {
    override suspend fun getPointOfInterest(id: String): Either<PointOfInterestAPIError, PointOfInterest> {
        TODO("Not yet implemented")
    }

    override suspend fun registerPointOfInterest(
        poi: PointOfInterest,
    ): Either<PointOfInterestAPIError, PointOfInterest> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePointOfInterest(id: String): Either<PointOfInterestAPIError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPointsOfInterest(): Either<PointOfInterestAPIError, List<PointOfInterest>> {
        TODO("Not yet implemented")
    }
}
