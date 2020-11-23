package infrastructure.storage.cassandra.tables.files

import com.outworkers.phantom.dsl._
import domain.file.StoredFileData
import infrastructure.storage.cassandra.tables.TableCrudOps
import zio.IO.fromFuture
import zio.Task

abstract class StoredFileDataTable extends Table[StoredFileDataTable, StoredFileData]
  with RootConnector
  with TableCrudOps[StoredFileData] {

  object dataId extends StringColumn with PartitionKey

  object data extends BlobColumn

  override lazy val tableName = "data"

  override def createEntity(entity: StoredFileData): Task[Option[StoredFileData]] = fromFuture {
    implicit ec =>
      insert
        .value(_.dataId, entity.dataId)
        .value(_.data, entity.data)
        .future()
        .map(result => if (result.isExhausted()) Option(entity) else None)
  }

  override def readEntity(dataId: String): Task[Option[StoredFileData]] =
    fromFuture {
      implicit ec =>
        select
          .where(_.dataId eqs dataId)
          .one()
    }

  override def getEntities: Task[Seq[StoredFileData]] = fromFuture { implicit ec =>
    select
      .all()
      .fetch()
  }

  override def deleteEntity(entity: StoredFileData): Task[Option[StoredFileData]] = fromFuture {
    implicit ec =>
      delete
        .where(_.dataId eqs entity.dataId)
        .future()
        .map(result => if (result.isExhausted()) Option(entity) else None)
  }
}


