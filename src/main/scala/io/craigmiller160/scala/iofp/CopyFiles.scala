package io.craigmiller160.scala.iofp

import cats.effect.IO
import java.io.File

object CopyFiles {
  def copy(origin: File, destination: File): IO[Long] = ???
}
