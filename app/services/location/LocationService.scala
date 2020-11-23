package services.location

import domain.location.Location
import infrastructure.storage.cassandra.db.CassandraDB._
import repositories.RepoError.{ReadError, WriteError}
import repositories.{CrudService, RepoError}
import zio.{Has, IO, ZIO, ZLayer}

object LocationService {
}
