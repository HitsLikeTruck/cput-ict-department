package infrastructure.storage.cassandra.tables.location

import com.outworkers.phantom.dsl._
import domain.location.{Location, LocationType}
import infrastructure.storage.cassandra.tables.TableCrudOps
import zio.IO._
import zio.Task

abstract class LocationTable extends Table[LocationTable, Location]
  with RootConnector
  with TableCrudOps[Location] {
  object id extends StringColumn with PartitionKey

  object locationType extends JsonColumn[LocationType]

  object name extends StringColumn

  object locatedIn extends OptionalJsonColumn[Location]

  override lazy val tableName = "locations"

  override def createEntity(entity: Location): Task[Option[Location]] =
    fromFuture {
      implicit ec =>
        insert
          .value(_.id, entity.id)
          .value(_.name, entity.name)
          .value(_.locationType, entity.locationType)
          .value(_.locatedIn, entity.locatedIn)
          .future()
          .map(result => if (result.isExhausted()) Option(entity) else None)
    }

  override def readEntity(id: String): Task[Option[Location]] =
    fromFuture { implicit ec =>
      select
        .where(_.id eqs id)
        .one()
    }

  override def getEntities: Task[Seq[Location]] = fromFuture { implicit ec =>
    select
      .all()
      .fetch()
  }

  override def deleteEntity(entity: Location): Task[Option[Location]] = fromFuture { implicit ec =>
    delete
      .where(_.id eqs entity.id)
      .future()
      .map(result => if (result.isExhausted()) Option(entity) else None)
  }


}
