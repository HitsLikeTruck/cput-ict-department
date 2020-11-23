package infrastructure.storage.cassandra.tables.reports

import com.outworkers.phantom.dsl.Primitive
import domain.reports.{ReportType, Reports}
import io.circe.jawn._
import io.circe.syntax._


object ReportImplicits {
  implicit val report: Primitive[Reports] = {
    Primitive.json[Reports](_.asJson.noSpaces)(decode[Reports](_).toOption.get)
  }
  implicit val reportType: Primitive[ReportType] = {
    Primitive.json[ReportType](_.asJson.noSpaces)(decode[ReportType](_).toOption.get)
  }
}
