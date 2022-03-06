package io.craigmiller160.scala.iofp

import cats._
import cats.implicits._
import cats.effect._

object Runner extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    IO.println("Hello World")
      .as(ExitCode.Success)
  }
}
