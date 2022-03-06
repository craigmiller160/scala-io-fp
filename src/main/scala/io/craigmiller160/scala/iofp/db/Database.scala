package io.craigmiller160.scala.iofp.db

import cats.effect.{Resource, Sync}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

import java.sql.Connection
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

  def makeDbResource[F[_]: Sync]: Resource[F,Connection] = {
    val ds = getDataSource
    Resource.make(acquire = Sync[F].blocking(ds.getConnection))(release = conn => Sync[F].blocking(conn.close()))
  }
}
