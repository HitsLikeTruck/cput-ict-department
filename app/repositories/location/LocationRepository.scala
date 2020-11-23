package repositories.location

import domain.location.Location
import infrastructure.storage.cassandra.db.CassandraDB._
import repositories.RepoError.{ReadError, WriteError}
import repositories.{CrudService, RepoError}
import zio.{Has, IO, ZIO, ZLayer}

object LocationRepository {
  private type LocationRepository = Has[LocationRepository.Service]
  private type Dependencies = LocationRepository

  trait Service extends CrudService[Location]

  val live = ZLayer.succeed {
    new Service {
      override def createEntity(entity: Location): IO[RepoError, Option[Location]] =
        LocationTable
          .createEntity(entity)
          .mapError(WriteError)

      override def readEntity(id: String): IO[RepoError, Option[Location]] =
        LocationTable
          .readEntity(id)
          .mapError(ReadError)

      override def readEntities: IO[RepoError, Seq[Location]] =
        LocationTable
          .getEntities
          .mapError(ReadError)

      override def deleteEntity(entity: Location): IO[RepoError, Option[Location]] =
        LocationTable
          .deleteEntity(entity)
          .mapError(WriteError)
    }
  }

  def createEntity(entity: Location): ZIO[Dependencies, RepoError, Option[Location]] =
    ZIO.accessM(_.get.createEntity(entity))

  def readEntity(id: String): ZIO[Dependencies, RepoError, Option[Location]] =
    ZIO.accessM(_.get.readEntity(id))

  def readEntities: ZIO[Dependencies, RepoError, Seq[Location]] =
    ZIO.accessM(_.get.readEntities)

  def deleteEntity(entity: Location): ZIO[Dependencies, RepoError, Option[Location]] =
    ZIO.accessM(_.get.deleteEntity(entity))

}
