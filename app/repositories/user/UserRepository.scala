package repositories.user

import domain.users.User
import infrastructure.storage.cassandra.db.CassandraDB._
import repositories.{CrudService, RepoError}
import repositories.RepoError.{ReadError, WriteError}
import zio.{Has, IO, ZIO, ZLayer}

object UserRepository {
  private type userRepository = Has[UserRepository.Service]
  private type Dependencies = userRepository

  trait Service extends CrudService[User]

  val live = ZLayer.succeed {
    new Service {
      override def createEntity(entity: User): IO[RepoError, Option[User]] =
        UserTable
          .createEntity(entity)
          .mapError(WriteError)

      override def readEntity(id: String): IO[RepoError, Option[User]] =
        UserTable
          .readEntity(id)
          .mapError(ReadError)

      override def readEntities: IO[RepoError, Seq[User]] =
        UserTable
          .getEntities
          .mapError(ReadError)

      override def deleteEntity(entity: User): IO[RepoError, Option[User]] =
        UserTable
          .deleteEntity(entity)
          .mapError(WriteError)
    }
  }

  def createEntity(entity: User): ZIO[Dependencies, RepoError, Option[User]] =
    ZIO.accessM(_.get.createEntity(entity))

  def readEntity(id: String): ZIO[Dependencies, RepoError, Option[User]] =
    ZIO.accessM(_.get.readEntity(id))

  def readEntities: ZIO[Dependencies, RepoError, Seq[User]] =
    ZIO.accessM(_.get.readEntities)

  def deleteEntity(entity: User): ZIO[Dependencies, RepoError, Option[User]] =
    ZIO.accessM(_.get.deleteEntity(entity))
}
