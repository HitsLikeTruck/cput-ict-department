package domain.reports

import java.time.LocalDateTime

import domain.status.Status
import domain.users.User
import io.circe._
import io.circe.generic.semiauto._

case class Validations(
                        report: Reports,
                        status: Status,
                        modifiedBy: User,
                        comment: Option[String],
                        dateTime: LocalDateTime = LocalDateTime.now
                      )

object Validations {
  type JsonEntity = Validations
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]

  implicit object localDateTime extends Ordering[LocalDateTime] {
    def compare(x: LocalDateTime, y: LocalDateTime): Int = y.compareTo(x)
  }

  val orderbyDateTime: Ordering[Validations] = Ordering.by(a => a.dateTime)
}
