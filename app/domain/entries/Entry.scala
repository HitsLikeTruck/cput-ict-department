package domain.entries

import java.time.LocalDateTime

import domain.reports.Reports
import io.circe._
import io.circe.generic.semiauto._


case class Entry(
                  id: String,
                  comment: String,
                  finding: String,
                  report: Reports,
                  date: LocalDateTime = LocalDateTime.now()
                )

object Entry {
  type JsonEntity = Entry
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
}


