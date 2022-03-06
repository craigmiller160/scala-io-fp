package io.craigmiller160.scala.iofp

import cats.effect.{Resource, Sync}

import java.sql.Connection
import javax.sql.DataSource
import cats.implicits._
import cats.effect._
import cats._

object Users {
  def make[F[_]: Sync](dbResource: Resource[F, Connection]): Users[F] = new Users[F] {
    override def findAllFriends[F[_] : Sync](userId: Long): F[List[User]] = {
      val result = dbResource.use[List[User]] { conn =>
        Sync[F].blocking(conn.createStatement().executeQuery("SELECT * FROM people"))
          .map(resultSet => List(new User {}))
      }
      result
    }
  }
//  def findAllFriends(userId: UserId): F[List[User]] = dbResource.use { db => // Db => F[A].
//    Sync[F].delay(db.runQuery("SELECT blah blah blah")).map(resultSet: ResultSet => resultSet.rows.map(row => User(row.name, row.age))) // F[List[User]]
}
trait Users[F[_]] {
  def findAllFriends[F[_]](userId: Long): F[List[User]]
}
trait User {}