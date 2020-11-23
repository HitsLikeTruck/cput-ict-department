package domain.users

import java.time.LocalDateTime

import io.circe._
import io.circe.generic.semiauto._

/**
  * since we are going to use Outlook for authentication, this entity looks unnecessary.
  * @param userId
  * @param password
  * @param date
  */
case class UserPassword(
                         userId: String,
                        password: String,
                        date: LocalDateTime = LocalDateTime.now
                       )

object UserPassword {
  type JsonEntity = UserPassword
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}

