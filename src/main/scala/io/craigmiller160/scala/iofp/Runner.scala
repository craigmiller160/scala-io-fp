package io.craigmiller160.scala.iofp

import cats._
import cats.implicits._
import cats.effect._

object Runner extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    IO.println("Hello")
      .flatMap(_ => IO.println("World"))
      .flatMap(_ => IO.delay(() => "FooBar"))
      .flatMap(msgFn => IO.println(msgFn()))
      .flatMap(_ => IO.blocking(() => "ABC"))
      .flatMap(msgFn => IO.println(msgFn()))
      .as(ExitCode.Success)
  }
}
