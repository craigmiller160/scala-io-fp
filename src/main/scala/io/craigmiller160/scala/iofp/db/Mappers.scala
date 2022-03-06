package io.craigmiller160.scala.iofp.db

import io.craigmiller160.scala.iofp.model.Person
import io.craigmiller160.scala.iofp.types.JdbcMapper

import java.util.UUID

object Mappers {
  val personMapper: JdbcMapper[Person] = { rs =>
    Person(
      id = UUID.fromString(rs.getString("id")),
      firstName = rs.getString("first_name"),
      lastName = rs.getString("last_name")
    )
  }
}
