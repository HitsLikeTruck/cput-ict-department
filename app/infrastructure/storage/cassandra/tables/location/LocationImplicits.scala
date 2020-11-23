package infrastructure.storage.cassandra.tables.location

import com.outworkers.phantom.dsl._
import domain.location.{Location, LocationType}
import io.circe.parser._
import io.circe.syntax._


object LocationImplicits {
  implicit val location: Primitive[Location] = {
    Primitive.json[Location](_.asJson.noSpaces)(decode[Location](_).toOption.get)
  }

  implicit val locationType: Primitive[LocationType] = {
    Primitive.json[LocationType](_.asJson.noSpaces)(decode[LocationType](_).toOption.get)
  }

}
