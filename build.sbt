ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"
val scalaTestVersion = "3.2.11"
val catsVersion = "2.7.0"
val mouseVersion = "1.0.10"
val catsEffectVersion = "3.3.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "mouse" % mouseVersion,
  "org.typelevel" %% "cats-effect" % catsEffectVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "scala-io-fp"
  )

Compile / run / mainClass := Some("io.craigmiller160.scala.iofp.Runner")