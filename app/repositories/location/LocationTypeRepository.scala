package repositories.location

import domain.location.LocationType
import infrastructure.storage.cassandra.db.CassandraDB._
import repositories.RepoError.{ReadError, WriteError}
import repositories.{CrudService, RepoError}
import zio.{Has, IO, ZIO, ZLayer}


object LocationTypeRepository {
  private type LocationTypeRepository = Has[LocationTypeRepository.Service]
  private type Dependencies = LocationTypeRepository

  trait Service extends CrudService[LocationType]

  val live = ZLayer.succeed {
    new Service {
      override def createEntity(entity: LocationType): IO[RepoError, Option[LocationType]] =
        LocationTypeTable
          .createEntity(entity)
          .mapError(WriteError)

      override def readEntity(id: String): IO[RepoError, Option[LocationType]] =
        LocationTypeTable
          .readEntity(id)
          .mapError(ReadError)

      override def readEntities: IO[RepoError, Seq[LocationType]] =
        LocationTypeTable
          .getEntities
          .mapError(ReadError)

      override def deleteEntity(entity: LocationType): IO[RepoError, Option[LocationType]] =
        LocationTypeTable
          .deleteEntity(entity)
          .mapError(WriteError)
    }
  }

  def createEntity(entity: LocationType): ZIO[Dependencies, RepoError, Option[LocationType]] =
    ZIO.accessM(_.get.createEntity(entity))

  def readEntity(id: String): ZIO[Dependencies, RepoError, Option[LocationType]] =
    ZIO.accessM(_.get.readEntity(id))

  def readEntities: ZIO[Dependencies, RepoError, Seq[LocationType]] =
    ZIO.accessM(_.get.readEntities)

  def deleteEntity(entity: LocationType): ZIO[Dependencies, RepoError, Option[LocationType]] =
    ZIO.accessM(_.get.deleteEntity(entity))

}
