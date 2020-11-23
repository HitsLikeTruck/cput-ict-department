package infrastructure.storage.cassandra.db

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.dsl._
import environment.connection.DataConnection
import zio.IO.fromFuture
import zio.Task

import scala.concurrent.Future

object DatabaseSetUp {
  implicit def keyspace: KeySpace = DataConnection.keySpaceQuery.keySpace

  implicit def session: Session = DataConnection.connector.session

  def createTables: Task[Boolean] = fromFuture {
    implicit ec =>
      createSchema()
  }

  /**
   * Create organisation tables
   *
   * @return
   */
  private def createSchema(): Future[Boolean] = {
    CassandraDB.LocationTypeTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    CassandraDB.LocationTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    CassandraDB.ReportsTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    CassandraDB.ReportTypeTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    CassandraDB.UserTable.create.ifNotExists().future().map(result=> result.head.isExhausted())
  }
}
