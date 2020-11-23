package domain.location

import io.circe._
import io.circe.generic.semiauto._

case class Location(
                     id:String,
                     name: String,
                     locationType: LocationType,
                     locatedIn: Option[Location])
object  Location{
  type JsonEntity = Location
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
