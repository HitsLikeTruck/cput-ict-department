package infrastructure.storage.cassandra.db

import com.outworkers.phantom.dsl._
import environment.connection.DataConnection
import infrastructure.storage.cassandra.tables.applogs.AppLogTable
import infrastructure.storage.cassandra.tables.location.{LocationTable, LocationTypeTable}
import infrastructure.storage.cassandra.tables.reports.{ReportTypeTable, ReportsTable}
import infrastructure.storage.cassandra.tables.user.UserTable

class CassandraDB(override val connector: KeySpaceDef) extends Database[CassandraDB](connector) {

  // Locations
  object LocationTable extends LocationTable with connector.Connector

  object LocationTypeTable extends LocationTypeTable with connector.Connector

  // Reports
  object ReportsTable extends ReportsTable with connector.Connector

  //ReportType
  object ReportTypeTable extends ReportTypeTable with connector.Connector

  // Logs
  object AppLogTable extends AppLogTable with connector.Connector

  //User
  object UserTable extends  UserTable with connector.Connector
}

object CassandraDB extends CassandraDB(DataConnection.connector)

