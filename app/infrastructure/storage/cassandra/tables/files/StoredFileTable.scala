package infrastructure.storage.cassandra.tables.files

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import domain.file.StoredFile
import infrastructure.storage.cassandra.tables.TableCrudOps
import zio.IO.fromFuture
import zio.Task

abstract class StoredFileTable extends Table[StoredFileTable, StoredFile]
  with RootConnector
  with TableCrudOps[StoredFile] {

  object fileId extends StringColumn with PartitionKey

  object dataOrder extends IntColumn with PrimaryKey

  object fileName extends OptionalStringColumn

  object fileSize extends OptionalLongColumn

  object url extends OptionalStringColumn

  object dataId extends StringColumn

  object fileType extends OptionalStringColumn

  object date extends Col[LocalDateTime]

  override lazy val tableName = "files"

  def getEntity(fileId: String): Task[List[StoredFile]] = fromFuture {
    implicit ec =>
      select
        .where(_.fileId eqs fileId)
        .fetch()
  }

  override def createEntity(entity: StoredFile): Task[Option[StoredFile]] = fromFuture {
    implicit ec =>
      insert
        .value(_.dataId, entity.dataId)
        .value(_.dataOrder, entity.dataOrder)
        .value(_.date, entity.date)
        .value(_.fileId, entity.fileId)
        .value(_.fileName, entity.fileName)
        .value(_.fileSize, entity.fileSize)
        .value(_.fileType, entity.fileType)
        .value(_.url, entity.url)
        .future()
        .map(result => if (result.isExhausted()) Option(entity) else None)
  }

  override def readEntity(id: String): Task[Option[StoredFile]] = fromFuture {
    implicit ec =>
      select
        .where(_.dataId eqs dataId)
        .one()
  }

  override def getEntities: Task[Seq[StoredFile]] = fromFuture { implicit ec =>
    select
      .all()
      .fetch()
  }

  override def deleteEntity(entity: StoredFile): Task[Option[StoredFile]] =
    fromFuture {
      implicit ec =>
        delete
          .where(_.fileId eqs entity.fileId)
          .future()
          .map(result => if (result.isExhausted()) Option(entity) else None)
    }

}

