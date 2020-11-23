package infrastructure.storage.cassandra.tables.applogs

import com.outworkers.phantom.dsl.Primitive
import domain.applogs.LogType
import io.circe.parser.decode
import io.circe.syntax.EncoderOps

object AppLogImplicits {

  implicit val logType: Primitive[LogType] = {
    Primitive
      .json[LogType](_.asJson.noSpaces)(decode[LogType](_)
        .toOption.get)
  }
}
