package controllers.location

import domain.location.Location
import play.api.mvc._
import javax.inject.Inject
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext

class LocationController @Inject()(cc: ControllerComponents, api: ApiResponse)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  type DomainObject = Location

  def className: String = "LocationController"

  def createLocation: Action[JsValue] = Action.async(parse.json) {

  }


}
