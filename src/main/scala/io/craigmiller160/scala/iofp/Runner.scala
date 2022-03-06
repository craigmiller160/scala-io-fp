package io.craigmiller160.scala.iofp

import cats._
import cats.implicits._
import cats.effect._
import io.craigmiller160.scala.iofp.db.Database
import io.craigmiller160.scala.iofp.db.DbImplicits._
import io.craigmiller160.scala.iofp.model.Person
import io.craigmiller160.scala.iofp.types._
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import mouse.all._

import java.util.UUID

object Runner extends IOApp {
  private implicit def unsafeLogger[F[_]: Sync] = Slf4jLogger.getLogger[F]

  override def run(args: List[String]): IO[ExitCode] = {
    query()
      .flatMap(people =>
        people.map(person => Logger[IO].info(person.toString)) |>
          ioMonoid[Unit]().combineAll
      )
      .as(ExitCode.Success)
  }

  private def ioMonoid[A](): Monoid[IO[A]] = new Monoid[IO[A]] {
    override def empty: IO[A] = IO.unit
    override def combine(io1: IO[A], io2: IO[A]): IO[A] =
      io1.flatMap(_ => io2)
  }

  private val personMapper: JdbcMapper[Person] = { rs =>
    Person(
      id = UUID.fromString(rs.getString("id")),
      firstName = rs.getString("first_name"),
      lastName = rs.getString("last_name")
    )
  }

  private def query(): IO[List[Person]] = {
    Database.makeDbResource[IO].use(conn => {
      IO.blocking(conn.createStatement().executeQuery("SELECT * FROM people"))
        .map(rs => rs.map(personMapper))
    })
  }
}
