package infrastructure.storage.cassandra.tables.user

import domain.users.User
import infrastructure.storage.cassandra.tables.TableCrudOps
import zio.Task
import zio.IO._
import com.outworkers.phantom.dsl._

//Rachael Klein 218 057 377
abstract class UserTable extends Table[UserTable, User]
  with RootConnector
  with TableCrudOps[User] {
  override lazy val tableName = "user"

  object idNumber extends StringColumn with PartitionKey

  object email extends StringColumn

  object firstName extends StringColumn

  object middleName extends StringColumn

  object lastName extends StringColumn

  override def createEntity(entity: User): Task[Option[User]] =
    fromFuture {
      implicit ec =>
        insert
          .value(_.idNumber, entity.idNumber)
          .value(_.firstName, entity.names.firstName)
          .value(_.middleName, entity.names.middleName)
          .value(_.lastName, entity.names.lastName)
          .value(_.email, entity.email)
          .future()
          .map(
            result => if (result.isExhausted()) Option(entity) else None)
    }

  override def readEntity(idNumber: String): Task[Option[User]] =
    fromFuture {
      implicit ec =>
        select
          .where(_.idNumber eqs idNumber)
          .one()
    }

  override def getEntities: Task[Seq[User]] = fromFuture { implicit ec =>
    select
      .all()
      .fetch()
  }

  override def deleteEntity(entity: User): Task[Option[User]] = fromFuture { implicit ec =>
    delete
      .where(_.idNumber eqs entity.idNumber)
      .future()
      .map(result => if (result.isExhausted()) Option(entity) else None)
  }
}
