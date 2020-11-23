package infrastructure.storage.cassandra.tables.entry

import java.time.LocalDateTime

import domain.entries.Actions
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import domain.applogs.{AppLog, LogType}
import zio.IO.fromFuture
import zio.Task

abstract class DeficiencyTable extends Table [ DeficiencyTable,Actions ] with RootConnector {

  object id extends StringColumn with PrimaryKey

  object logType extends JsonColumn[LogType]

  object message extends StringColumn

  object date extends Col[LocalDateTime]

  override lazy val tableName = "DeficiencyTable"

  def saveLog(entity: DeficiencyTable): Task[Option[DeficiencyTable]] = fromFuture { implicit ec =>
    insert
      .value(_.id, entity.id)
      .value(_.logType, entity.logType)
      .value(_.message, entity.message)
      .value(_.date, entity.date)
      .future()
      .map(result => if (result.isExhausted()) Option(entity) else None)
  }

  def getLog(id: String): Task[Option[DeficiencyTable]] = fromFuture { implicit ec =>
    select
      .where(_.id eqs id)
      .one()
  }

  def getAllLogs: Task[Seq[DeficiencyTable]] = fromFuture { implicit ec =>
    select
      .all()
      .fetch()
  }

  def deleteEntity(entity: DeficiencyTable): Task[Option[DeficiencyTable]] = fromFuture { implicit ec =>
    delete
      .where(_.id eqs entity.id)
      .future()
      .map(result => if (result.isExhausted()) Option(entity) else None)
  }
}