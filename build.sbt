ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"
val scalaTestVersion = "3.2.11"
val catsVersion = "2.7.0"
val mouseVersion = "1.0.10"
val catsEffectVersion = "3.3.6"
val hikariVersion = "4.0.3"
val pgVersion = "42.3.3"
val slf4jVersion = "1.7.36"
val log4jVersion = "2.17.2"
val log4CatsVersion = "2.2.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "mouse" % mouseVersion,
  "org.typelevel" %% "cats-effect" % catsEffectVersion,
  "com.zaxxer" % "HikariCP" % hikariVersion exclude("org.slf4j", "slf4j-api"),
  "org.postgresql" % "postgresql" % pgVersion,
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
  "org.typelevel" %% "log4cats-core" % log4CatsVersion,
  "org.typelevel" %% "log4cats-slf4j" % log4CatsVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "scala-io-fp"
  )

Compile / run / mainClass := Some("io.craigmiller160.scala.iofp.Runner")