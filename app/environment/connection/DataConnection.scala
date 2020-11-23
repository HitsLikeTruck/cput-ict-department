package environment.connection

import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy
import com.datastax.driver.core.{PoolingOptions, SocketOptions}
import com.outworkers.phantom.builder.serializers.KeySpaceSerializer
import com.outworkers.phantom.connectors.ContactPoints
import com.outworkers.phantom.dsl._
import com.typesafe.config.{Config, ConfigFactory}

import scala.jdk.CollectionConverters._

object DataConnection {
  lazy val connector: CassandraConnection = if (isProduction) prodConnector else devConnector
  val config: Config = ConfigFactory.load()
  val baseUrl: String = config.getString("base.url")
  // File Size
  val chunkSize: Int = config.getInt("cassandra.chunkSize")
  // HOSTS
  val developmentHost: Seq[String] = config.getStringList("cassandra.developmentHost").asScala.toList
  val productionHost: Seq[String] = config.getStringList("cassandra.productionHost").asScala.toList
  // Data Centers
  val dataCenter1: String = config.getString("cassandra.dataCenter1")
  val dataCenter2: String = config.getString("cassandra.dataCenter2")
  // Environment
  val isProduction: Boolean = config.getBoolean("cassandra.isProduction")
  val keySpace: String = config.getString("cassandra.keySpace")
  // CLUSTERS
  val productionClusterName: String = config.getString("cassandra.productionClusterName")
  val developmentClusterName: String = config.getString("cassandra.developmentClusterName")
  val keySpaceQuery: KeySpaceSerializer = if (isProduction) prodKeySpaceQuery else devKeySpaceQuery

  def prodConnector: CassandraConnection = ContactPoints(productionHost, PORT)
    .withClusterBuilder(
      _.withClusterName(productionClusterName)
        .withSocketOptions(new SocketOptions()
          .setReadTimeoutMillis(readTimeoutMillis)
          .setConnectTimeoutMillis(connectionTimeoutMillis))
        .withPoolingOptions(new PoolingOptions()
          .setMaxQueueSize(100000)
          .setPoolTimeoutMillis(20000))
        .withLoadBalancingPolicy(
          new DCAwareRoundRobinPolicy.Builder()
            .withUsedHostsPerRemoteDc(1)
            .withLocalDc(dataCenter1).build()))
    .noHeartbeat()
    .keySpace(prodKeySpaceQuery)

  def PORT = 9042

  def connectionTimeoutMillis = 70000000

  def readTimeoutMillis = 150000000

  def prodKeySpaceQuery: KeySpaceSerializer = KeySpace(keySpace).ifNotExists()
    .`with`(replication eqs NetworkTopologyStrategy
      .data_center(dataCenter1, 3)
      .data_center(dataCenter2, 2)
    ).and(durable_writes eqs true)

  def devConnector: CassandraConnection = ContactPoints(developmentHost, PORT)
    .withClusterBuilder(
      _.withClusterName(productionClusterName)
        .withSocketOptions(new SocketOptions()
          .setConnectTimeoutMillis(20000)
          .setReadTimeoutMillis(20000)))
    .noHeartbeat()
    .keySpace(devKeySpaceQuery)

  def devKeySpaceQuery: KeySpaceSerializer = KeySpace(keySpace).ifNotExists()
    .`with`(replication eqs SimpleStrategy.replication_factor(1))
    .and(durable_writes eqs true)
}
