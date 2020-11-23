package infrastructure.storage.cassandra.tables.reports

import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import domain.reports.Reports
import infrastructure.storage.cassandra.tables.TableCrudOps
import zio.Task
import zio.IO._

abstract class ReportsTable extends Table[ReportsTable, Reports]
  with RootConnector
  with TableCrudOps[Reports] {
  override lazy val tableName = "reports"

  override def createEntity(entity: Reports): Task[Option[Reports]] =
    fromFuture {
      implicit ec =>
        insert
          .value(_.reportId, entity.reportId)
          .value(_.reportTypeId, entity.reportId)
          .value(_.description, entity.description)
          .value(_.date, entity.date)
          .value(_.permission, entity.permission)
          .future()
          .map(
            result => if (result.isExhausted()) Option(entity) else None)
    }

  override def readEntity(reportId: String): Task[Option[Reports]] =
    fromFuture {
      implicit ec =>
        select
          .where(_.reportId eqs reportId)
          .one()
    }

  override def getEntities: Task[Seq[Reports]] = fromFuture { implicit ec =>
    select
      .all()
      .fetch()
  }

  override def deleteEntity(entity: Reports): Task[Option[Reports]] = fromFuture { implicit ec =>
    delete
      .where(_.reportId eqs entity.reportId)
      .future()
      .map(result => if (result.isExhausted()) Option(entity) else None)
  }

  object reportId extends StringColumn with PartitionKey

  object reportTypeId extends StringColumn

  object description extends StringColumn

  object date extends DateTimeColumn

  object permission extends StringColumn

}