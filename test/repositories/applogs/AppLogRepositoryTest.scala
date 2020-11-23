package repositories.applogs

import com.typesafe.scalalogging.Logger
import data.AppTest
import domain.applogs.AppLog
import org.slf4j.LoggerFactory
import zio.random.Random
import zio.test.Assertion.isSome
import zio.test.magnolia.DeriveGen
import zio.test._

object AppLogRepositoryTest extends AppTest {
  val testName = this.getClass.getName
  val log = Logger(LoggerFactory.getLogger(testName))
  val testData: Gen[Random with Sized, AppLog] = DeriveGen[AppLog]

  override def spec: ZSpec[Environment, Failure] =
    suite(testName) {
      testM(testName) {
        checkNM(1)(testData) { data =>
          val program = for {
            create <- AppLogRepository.saveLog(data)
            _ <- AppLogRepository.getLog(data.id)
            result <- AppLogRepository.deleteEntity(data)
          } yield {
            assert(create)(isSome) &&
              assert(result)(isSome)
          }
          program.provideLayer(
            AppLogRepository.live
          )
        }
      }
    }
}
