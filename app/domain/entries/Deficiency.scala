package domain.entries

import java.time.LocalDateTime

import io.circe._
import io.circe.generic.semiauto._

case class Deficiency(
                       id: String,
                       description: String,
                       entry: Entry,
                       date: LocalDateTime = LocalDateTime.now
                     )

object Deficiency {
  type JsonEntity = Deficiency
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}

