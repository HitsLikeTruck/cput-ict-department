package domain.applogs

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor, Json}

case class AppLog(id: String,
                  logType: LogType,
                  message: String,
                  date: LocalDateTime)

object AppLog {
  implicit val decodeZone = deriveDecoder[AppLog]
  implicit val encodeZone = deriveEncoder[AppLog]
  implicit val encodeAppLog: Encoder[LocalDateTime] =
    (localDateTime: LocalDateTime) =>
      Json.fromString(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
  implicit val decodeAppLog: Decoder[LocalDateTime] =
    (hCursor: HCursor) =>
      hCursor.value.as[String].map(LocalDateTime.parse(_, DateTimeFormatter.ISO_DATE_TIME))
}
