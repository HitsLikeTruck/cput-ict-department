package repositories.applogs

import domain.applogs.AppLog
import infrastructure.storage.cassandra.db.CassandraDB._
import repositories.RepoError
import repositories.RepoError.{ReadError, WriteError}
import zio.{Has, IO, ZIO, ZLayer}

object AppLogRepository {

  type AppLogRepository = Has[AppLogRepository.Service]

  trait Service {
    def saveLog(entity: AppLog): IO[RepoError, Option[AppLog]]

    def getLog(id: String): IO[RepoError, Option[AppLog]]

    def getAllLogs: IO[RepoError, Seq[AppLog]]

    def deleteEntity(entity: AppLog): IO[RepoError, Option[AppLog]]
  }

  val live = ZLayer.succeed {
    new Service {
      override def saveLog(entity: AppLog): IO[RepoError, Option[AppLog]] =
        AppLogTable.saveLog(entity).mapError(WriteError)

      override def getLog(id: String): IO[RepoError, Option[AppLog]] =
        AppLogTable.getLog(id).mapError(ReadError)

      override def getAllLogs: IO[RepoError, Seq[AppLog]] =
        AppLogTable.getAllLogs.mapError(ReadError)

      override def deleteEntity(entity: AppLog): IO[RepoError, Option[AppLog]] =
        AppLogTable.deleteEntity(entity).mapError(WriteError)
    }
  }

  def saveLog(entity: AppLog): ZIO[AppLogRepository, RepoError, Option[AppLog]] =
    ZIO.accessM(_.get.saveLog(entity))

  def getLog(id: String): ZIO[AppLogRepository, RepoError, Option[AppLog]] =
    ZIO.accessM(_.get.getLog(id))

  def getAllLogs: ZIO[AppLogRepository, RepoError, Seq[AppLog]] =
    ZIO.accessM(_.get.getAllLogs)

  def deleteEntity(entity: AppLog): ZIO[AppLogRepository, RepoError, Option[AppLog]] =
    ZIO.accessM(_.get.deleteEntity(entity))
}


