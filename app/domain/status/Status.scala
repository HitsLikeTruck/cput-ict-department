package domain.status

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Status(
                 id:String,
                 name: String
                 )
object Status {
  type JsonEntity = Status
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
