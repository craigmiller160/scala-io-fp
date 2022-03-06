package io.craigmiller160.scala.iofp

import java.sql.ResultSet

package object types {
  type JdbcMapper[A] = ResultSet => A
}
