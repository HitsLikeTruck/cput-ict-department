package repositories

import zio.IO

trait CrudService[A] {

  def createEntity(entity: A): IO[RepoError, Option[A]]

  def readEntity(id: String): IO[RepoError, Option[A]]

  def readEntities: IO[RepoError, Seq[A]]

  def deleteEntity(entity: A): IO[RepoError, Option[A]]
}
