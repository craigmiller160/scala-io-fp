package io.craigmiller160.scala.iofp

import cats.effect._
import io.craigmiller160.scala.iofp.services.PeopleQueryService

object Runner extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    PeopleQueryService.queryForPeople()
      .as(ExitCode.Success)
  }

}
