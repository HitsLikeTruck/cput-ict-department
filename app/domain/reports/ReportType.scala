package domain.reports

import io.circe._
import io.circe.generic.semiauto._

case class ReportType(
                       id: String,
                       Name: String
                     )

object ReportType {
  type JsonEntity = ReportType
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
