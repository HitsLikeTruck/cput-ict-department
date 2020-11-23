package infrastructure.storage.cassandra.tables.location

import com.outworkers.phantom.dsl._
import domain.location.LocationType
import infrastructure.storage.cassandra.tables.TableCrudOps
import zio.IO.fromFuture
import zio.Task

abstract class LocationTypeTable extends Table[LocationTypeTable, LocationType]
  with RootConnector
  with TableCrudOps[LocationType] {

  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  override lazy val tableName = "locationtype"

  override def createEntity(entity: LocationType): Task[Option[LocationType]] =
    fromFuture {
      implicit ec =>
        insert
          .value(_.id, entity.id)
          .value(_.name, entity.name)
          .future()
          .map(result => if (result.isExhausted()) Option(entity) else None)
    }

  override def readEntity(id: String): Task[Option[LocationType]] = fromFuture { implicit ec =>
    select
      .where(_.id eqs id)
      .one()
  }

  override def getEntities: Task[Seq[LocationType]] =
    fromFuture { implicit ec =>
      select
        .all()
        .fetch()
    }

  override def deleteEntity(entity: LocationType): Task[Option[LocationType]] = fromFuture { implicit ec =>
    delete
      .where(_.id eqs entity.id)
      .future()
      .map(result => if (result.isExhausted()) Option(entity) else None)
  }
}
