package io.craigmiller160.scala.iofp.db

import cats.effect.{Resource, Sync}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

import javax.sql.DataSource

object Database {
  private val HOST = "localhost"
  private val PORT = "5432"
  private val USER = "user"
  private val PASSWORD = "password"
  private val DB = "postgres"

  def getDataSource: DataSource = {
    val config = new HikariConfig()
    config.setUsername(USER)
    config.setPassword(PASSWORD)
    config.setJdbcUrl(s"jdbc:postgresql://$HOST:$PORT/$DB")

    new HikariDataSource(config)
  }

  val db = getDataSource

  def makeDbResource[F[_]: Sync] = Resource.make(acquire = Sync[F].delay(db.getConnection))(release = conn => Sync[F].delay(conn.close()))
}
