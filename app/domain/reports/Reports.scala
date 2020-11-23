package domain.reports

import java.time.LocalDateTime

import io.circe._
import io.circe.generic._
import io.circe.generic.semiauto._

case class Reports(
                    id: String,
                    reportType: ReportType,
                    description: String,
                    date: LocalDateTime,
                  )

object Reports {
  type JsonEntity = Reports
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}
