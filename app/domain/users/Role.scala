package domain.users

import io.circe._
import io.circe.generic.semiauto._

case class Role(id: String,
                name: String,
                description: String
               )

object Role {
  type JsonEntity = Role

  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
