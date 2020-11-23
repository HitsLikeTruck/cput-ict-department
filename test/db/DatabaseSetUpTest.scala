package db

import environment.runtime.EffectRuntime._
import infrastructure.storage.cassandra.db.DatabaseSetUp
import org.scalatest.funsuite.AnyFunSuite

class DatabaseSetUpTest extends AnyFunSuite {
  test("testCreateTables") {
    runtime.unsafeRun(DatabaseSetUp.createTables)
  }

}
