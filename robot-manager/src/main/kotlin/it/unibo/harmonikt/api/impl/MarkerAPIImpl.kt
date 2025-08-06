package it.unibo.harmonikt.api.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import it.unibo.harmonikt.api.MarkerAPI
import it.unibo.harmonikt.api.MarkerAPIError
import it.unibo.harmonikt.api.dto.MarkerIdDTO
import it.unibo.harmonikt.model.Marker
import it.unibo.harmonikt.repository.MarkerRepository
import it.unibo.harmonikt.resources.PointOfInterests

/**
 * Implementation of the MarkerAPI interface for managing markers.
 *
 * @property markerRepository The repository for accessing marker data.
 * @property client The HTTP client used to make requests to the marker service.
 */
class MarkerAPIImpl(private val markerRepository: MarkerRepository<Marker>, private val client: HttpClient) :
    MarkerAPI {
    override suspend fun getAllMarkers(): Either<MarkerAPIError, List<Marker>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarkerInfo(
        poiId: PointOfInterests.Id,
        markerId: PointOfInterests.Id.Markers.Id,
    ): Either<MarkerAPIError, MarkerIdDTO> {
        TODO("Not yet implemented")
    }
}
