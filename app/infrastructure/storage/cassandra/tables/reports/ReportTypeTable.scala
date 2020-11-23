package infrastructure.storage.cassandra.tables.reports

import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import domain.reports.ReportType
import infrastructure.storage.cassandra.tables.TableCrudOps
import zio.Task
import zio.IO._

abstract class ReportTypeTable extends Table[ReportType, ReportTypeTable]
  with RootConnector
  with TableCrudOps[ReportType] {
  override lazy val tableName = "reportType"

  override def createEntity(entity: ReportType): Task[Option[ReportType]] =
    fromFuture {
      implicit ec =>
        insert
          .value(_.reportTypeId, entity.reportTypeId)
          .value(_.reportTypeName, entity.reportTypeName)
          .future()
          .map(result => if (result.isExhausted()) Option(entity) else None)
    }

  override def readEntity(id: String): Task[Option[ReportType]] =
    fromFuture {
      implicit ec =>
        select
          .where(_.reportTypeId eqs reportTypeId)
          .one()
    }

  override def getEntities: Task[Seq[ReportType]] =
    fromFuture { implicit ec =>
      select
        .all()
        .fetch()
    }

  override def deleteEntity(entity: ReportType): Task[Option[ReportType]] = fromFuture { implicit ec =>
    delete
      .where(_.reportTypeId eqs entity.reportTypeId)
      .future()
      .map(result => if (result.isExhausted()) Option(entity) else None)
  }

  object reportTypeId extends StringColumn with PartitionKey

  object reportTypeName extends StringColumn

}
