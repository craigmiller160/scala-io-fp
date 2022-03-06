package io.craigmiller160.scala.iofp

import cats._
import cats.implicits._
import cats.effect.{ExitCode, IO, IOApp}
import io.craigmiller160.scala.iofp.db.Database

object Runner extends IOApp {
  val ds = Database.getDataSource
  override def run(args: List[String]): IO[ExitCode] = ???
}
