package domain.file

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor, Json}

case class StoredFile(fileId: String,
                      dataOrder: Int,
                      fileName: Option[String],
                      fileSize: Option[Long],
                      url: Option[String],
                      dataId: String,
                      fileType: Option[String],
                      date: LocalDateTime)

object StoredFile {

  implicit val decodeZone = deriveDecoder[StoredFile]
  implicit val encodeZone = deriveEncoder[StoredFile]
  implicit val encodeAppLog: Encoder[LocalDateTime] =
    (localDateTime: LocalDateTime) =>
      Json.fromString(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
  implicit val decodeAppLog: Decoder[LocalDateTime] =
    (hCursor: HCursor) =>
      hCursor.value.as[String].map(LocalDateTime.parse(_, DateTimeFormatter.ISO_DATE_TIME))

}
