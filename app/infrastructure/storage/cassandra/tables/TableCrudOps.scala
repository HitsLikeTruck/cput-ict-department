package infrastructure.storage.cassandra.tables

import zio.Task

trait TableCrudOps[A] {

  def createEntity(entity: A): Task[Option[A]]

  def readEntity(id: String): Task[Option[A]]

  def getEntities: Task[Seq[A]]

  def deleteEntity(entity: A): Task[Option[A]]
}
