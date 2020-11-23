package domain.file

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class StoredFileData(dataId: String,
                          data: java.nio.ByteBuffer)

object StoredFileData {
  type JsonEntity = StoredFileData
  implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
  implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]

}
