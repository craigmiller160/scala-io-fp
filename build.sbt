ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"
val scalaTestVersion = "3.2.11"
val catsVersion = "2.7.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "org.typelevel" %% "cats-core" % catsVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "scala-io-fp"
  )
