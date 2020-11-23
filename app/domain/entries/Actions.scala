package domain.entries

import java.time.LocalDateTime

import domain.users.User
import io.circe._
import io.circe.generic.semiauto._

case class Actions(
                    id: String,
                    description: String,
                    resource: String,
                    entry: Entry,
                    action:String,
                    user: User,
                    date: LocalDateTime = LocalDateTime.now()
                  )

object Actions {
  type JsonEntity = Actions
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
