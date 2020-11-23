package domain.entries

import java.time.LocalDateTime

import domain.status.Status
import domain.users.User
import io.circe._
import io.circe.generic.semiauto._

case class Verification(
                         entry: Entry,
                         verifiedBy:User,
                         status: Status,
                         description: String,
                         date: LocalDateTime = LocalDateTime.now

                       )

object Verification {
  type JsonEntity = Verification
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
