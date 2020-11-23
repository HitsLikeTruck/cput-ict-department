package environment.runtime

import javax.inject.Singleton
import zio._

@Singleton
object EffectRuntime {
  lazy val runtime: Runtime[zio.ZEnv] = Runtime.default

}
