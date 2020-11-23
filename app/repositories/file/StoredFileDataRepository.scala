package repositories.file

import domain.file.StoredFileData
import infrastructure.storage.cassandra.db.CassandraDB._
import repositories.RepoError.{ReadError, WriteError}
import repositories.{CrudService, RepoError}
import zio.{Has, IO, ZIO, ZLayer}

object StoredFileDataRepository {
  private type StoredFileDataRepository = Has[StoredFileDataRepository.Service[StoredFileData]]
  private type Dependencies = StoredFileDataRepository

  trait Service[A] extends CrudService[A]
  val live = ZLayer.succeed {
    new Service[StoredFileData] {
      override def createEntity(entity: StoredFileData): IO[RepoError, Option[StoredFileData]] =
        StoredFileDataTable
          .createEntity(entity)
          .mapError(WriteError)

      override def readEntity(id: String): IO[RepoError, Option[StoredFileData]] =
        StoredFileDataTable
          .readEntity(id)
          .mapError(ReadError)

      override def readEntities: IO[RepoError, Seq[StoredFileData]] =
        StoredFileDataTable
          .getEntities
          .mapError(ReadError)

      override def deleteEntity(entity: StoredFileData): IO[RepoError, Option[StoredFileData]] =
        StoredFileDataTable
          .deleteEntity(entity)
          .mapError(WriteError)
    }
  }

  def createEntity(entity: StoredFileData): ZIO[Dependencies, RepoError, Option[StoredFileData]] =
    ZIO.accessM(_.get.createEntity(entity))

  def readEntity(id: String): ZIO[Dependencies, RepoError, Option[StoredFileData]] =
    ZIO.accessM(_.get.readEntity(id))

  def readEntities: ZIO[Dependencies, RepoError, Seq[StoredFileData]] =
    ZIO.accessM(_.get.readEntities)

  def deleteEntity(entity: StoredFileData): ZIO[Dependencies, RepoError, Option[StoredFileData]] =
    ZIO.accessM(_.get.deleteEntity(entity))


}
