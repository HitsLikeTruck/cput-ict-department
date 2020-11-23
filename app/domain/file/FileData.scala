package domain.file

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}


case class FileData(id: String,
                    url: String,
                    etag: Option[String],
                    size: Long,
                    contentType: Option[String])

object FileData {

  implicit val decodeZone = deriveDecoder[FileData]
  implicit val encodeZone = deriveEncoder[FileData]
}
