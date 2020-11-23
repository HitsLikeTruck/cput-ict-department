package api.location

import domain.location.Location
import repositories.location.LocationRepository
import services.jsonparse.JsonParseService
import services.security.RequestVerificationService

object LocationAPI {
  private val repository: LocationRepository.type = LocationRepository
  private val parseLayer: JsonParseService.type = JsonParseService
  private val verifyLayer: RequestVerificationService.type = RequestVerificationService
  private val Entity = Location


}
