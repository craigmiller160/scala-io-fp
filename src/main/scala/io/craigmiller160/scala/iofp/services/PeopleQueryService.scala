package io.craigmiller160.scala.iofp.services

import cats._
import cats.implicits._
import cats.effect._
import io.craigmiller160.scala.iofp.db.DbImplicits._
import io.craigmiller160.scala.iofp.db.{Database, Mappers}
import io.craigmiller160.scala.iofp.function.Monoids
import io.craigmiller160.scala.iofp.model.Person
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import mouse.all._

object PeopleQueryService {
  private implicit def unsafeLogger[F[_]: Sync] = Slf4jLogger.getLogger[F]

  def queryForPeople() = {
    query()
      .map(people =>
        people.map(person => person.toString) |>
          Monoids.stringMonoid.combineAll
      )
      .flatMap(peopleString => Logger[IO].info(peopleString))
  }

  private def query(): IO[List[Person]] = {
    Database.makeDbResource[IO].use(conn => {
      IO.blocking(conn.createStatement().executeQuery("SELECT * FROM people"))
        .map(rs => rs.map(Mappers.personMapper))
    })
  }
}
