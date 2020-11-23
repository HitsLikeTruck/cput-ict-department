package services.file

import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}
import java.time.LocalDateTime
import java.util.UUID

import domain.file._
import environment.connection.DataConnection
import environment.util.Util
import infrastructure.storage.cassandra.db.CassandraDB._
import repositories.RepoError
import repositories.RepoError.{ReadError, WriteError}
import zio._

object StoredFileRepository {
  type StoredFileRepository = Has[StoredFileRepository.Service]
  type Dependencies = StoredFileRepository
  val chunkSize = DataConnection.chunkSize

  trait Service {
    def storeFile(entity: FileInformation): IO[RepoError, Vector[FileData]]

    def getFileData(fileId: String): IO[RepoError, DataResponse]
  }
  val live: ZLayer[Any, Nothing, StoredFileRepository] = {
    ZLayer.succeed {
      new Service {
        override def storeFile(entity: FileInformation): IO[RepoError, Vector[FileData]] = {
          val chunks: Vector[Array[Byte]] = Files.
            readAllBytes(Paths.get(entity.file.path))
            .grouped(chunkSize)
            .toVector
          val fileId = Util.md5Hash(UUID.randomUUID().toString)
          ZIO.foreach(chunks) { chunk =>
            val dataId = Util.md5Hash(UUID.randomUUID().toString)
            for {
              _ <- StoredFileDataTable.createEntity(
                StoredFileData(
                  dataId,
                  ByteBuffer.wrap(chunk))).mapError(WriteError)
              _ <- StoredFileTable.createEntity(
                StoredFile(
                  fileId,
                  chunks.indexOf(chunk),
                  Option(entity.filename),
                  Option(entity.fileSize),
                  Option(DataConnection.baseUrl + "/" + fileId + "/" + entity.filename),
                  dataId,
                  entity.contentType,
                  LocalDateTime.now())).mapError(WriteError)
              fileData <- ZIO.succeed(
                FileData(
                  fileId,
                  DataConnection.baseUrl + "/" + fileId + "/" + entity.filename,
                  None,
                  entity.fileSize,
                  entity.contentType))
            } yield fileData
          }
        }

        override def getFileData(fileId: String): IO[RepoError, DataResponse] = for {
          files <- StoredFileTable
            .getEntity(fileId)
            .mapError(ReadError)
          data <- ZIO.foreach(files) { data =>
            for {
              dataFile <- StoredFileDataTable.readEntity(data.dataId).mapError(ReadError)
            } yield dataFile.get.data
          }
        } yield DataResponse(files.head, data)
      }
    }
  }

  def storeFile(entity: FileInformation): ZIO[Dependencies, RepoError, Vector[FileData]] =
    ZIO.accessM(_.get.storeFile(entity))

  def getFileData(fileId: String): ZIO[Dependencies, RepoError, DataResponse] =
    ZIO.accessM(_.get.getFileData(fileId))

}
