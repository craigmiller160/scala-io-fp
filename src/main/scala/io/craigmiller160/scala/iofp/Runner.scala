package io.craigmiller160.scala.iofp

import cats._
import cats.effect._
import cats.implicits._
import io.craigmiller160.scala.iofp.services.{BalanceService, PeopleQueryService}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Runner extends IOApp {
  private implicit def unsafeLogger[F[_]: Sync] = Slf4jLogger.getLogger[F]

  override def run(args: List[String]): IO[ExitCode] = {
    BalanceService.getBalance("Bob Saget")
      .flatMap(balance => Logger[IO].info(s"Balance: $$$balance"))
      .as(ExitCode.Success)
  }

}
