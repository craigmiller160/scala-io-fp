package io.craigmiller160.scala.iofp.db

import java.sql.ResultSet
import io.craigmiller160.scala.iofp.types._

object DbImplicits {
  implicit class ResultSetOps(rs: ResultSet) {
    private val itr = new Iterator[ResultSet] {
      override def hasNext: Boolean = rs.next()
      override def next(): ResultSet = rs
    }

    def map[A](mapper: JdbcMapper[A]): List[A] =
      itr.map(mapper).toList
  }
}
