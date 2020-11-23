package domain.applogs

import cats.syntax.functor._
import com.outworkers.phantom.dsl.Primitive
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.jawn.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

sealed trait LogType

object LogType {

  final case class RepositoryError(name: String = "RepositoryError") extends LogType

  final case class ServiceError(name: String = "ServiceError") extends LogType

  final case class ApiError(name: String = "ApiError") extends LogType

  final case class ControllerError(name: String = "ControllerError") extends LogType

  final case class ParserError(name: String = "ParserError") extends LogType

  implicit val encodeEvent: Encoder[LogType] = Encoder.instance {
    case repositoryError@RepositoryError(_) => repositoryError.asJson
    case serviceError@ServiceError(_) => serviceError.asJson
    case apiError@ApiError(_) => apiError.asJson
    case controllerError@ControllerError(_) => controllerError.asJson
    case parserError@ParserError(_) => parserError.asJson
  }
  implicit val logType: Primitive[LogType] = {
    Primitive
      .json[LogType](_.asJson.noSpaces)(decode[LogType](_)
        .toOption.get)
  }

  implicit val decodeEvent: Decoder[LogType] =
    List[Decoder[LogType]](
      Decoder[RepositoryError].widen,
      Decoder[ServiceError].widen,
      Decoder[ApiError].widen,
      Decoder[ControllerError].widen,
      Decoder[ParserError].widen,
    ).reduceLeft(_ or _)

  object RepositoryError {
    type JsonEntity = RepositoryError
    implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
    implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
  }

  object ServiceError {
    type JsonEntity = ServiceError
    implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
    implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
  }

  object ApiError {
    type JsonEntity = ApiError
    implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
    implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
  }

  object ControllerError {
    type JsonEntity = ControllerError
    implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
    implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
  }

  object ParserError {
    type JsonEntity = ParserError
    implicit val jsonDecoder: Decoder[JsonEntity] = deriveDecoder[JsonEntity]
    implicit val jsonEncoder: Encoder[JsonEntity] = deriveEncoder[JsonEntity]
  }

}



