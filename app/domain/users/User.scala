package domain.users

import io.circe._
import io.circe.generic.semiauto._

case class User(
                 email: String,
                 names: Names,
                 idNumber: String,
               )

object User {
  type JsonEntity = User
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
