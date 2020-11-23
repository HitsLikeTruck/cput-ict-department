name := """auditapi"""
organization := "za.ac.cput"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"
val PhantomVersion    = "2.59.0"
val catsVersion       = "2.2.0"
val circeVersion      = "0.13.0"
val sttpClientVersion = "2.2.8"
val zioVersion        = "1.0.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play"     %% "scalatestplus-play"        % "5.1.0"   % Test
libraryDependencies += "org.scalatestplus"          %% "scalacheck-1-14"           % "3.2.2.0" % Test

// ZIO LIBRARY
libraryDependencies += "dev.zio" %% "zio"         % zioVersion
libraryDependencies += "dev.zio" %% "zio-streams" % zioVersion

libraryDependencies += "dev.zio" %% "zio-test"          % zioVersion % Test
libraryDependencies += "dev.zio" %% "zio-test-sbt"      % zioVersion % Test
libraryDependencies += "dev.zio" %% "zio-test-magnolia" % zioVersion % Test

libraryDependencies += "com.outworkers" %% "phantom-dsl" % PhantomVersion
libraryDependencies += "com.outworkers" %% "phantom-connectors" % PhantomVersion
// https://mvnrepository.com/artifact/org.typelevel/cats-core
libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion
// https://mvnrepository.com/artifact/com.typesafe.scala-logging/scala-logging
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.11"
// https://mvnrepository.com/artifact/commons-io/commons-io
libraryDependencies += "commons-io" % "commons-io" % "2.8.0"




// Adds additional packages into Twirl
//TwirlKeys.templateImports += "zm.hashcode.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "zm.hashcode.binders._"

resolvers ++= Seq(
  "Typesafe repositories snapshots" at "https://repo.typesafe.com/typesafe/snapshots/",
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Typesafe repositories releases" at "https://repo.typesafe.com/typesafe/releases/",
  "softprops-maven" at "https://dl.bintray.com/content/softprops/maven",
  "Brando Repository" at "https://chrisdinn.github.io/releases/",
  "Sonatype repo" at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging" at "https://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository" at "https://download.java.net/maven/2/",
  "Twitter Repository" at "https://maven.twttr.com",
  "outworkers oss-releases" at "https://dl.bintray.com/outworkers/oss-releases/",
  "Goose Updates " at "https://dl.bintray.com/raisercostin/maven/",
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("public")
)