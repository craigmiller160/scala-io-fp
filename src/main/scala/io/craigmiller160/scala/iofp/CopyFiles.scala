package io.craigmiller160.scala.iofp

import cats.effect.{IO, Resource}

import java.io.{File, FileInputStream, FileOutputStream}

object CopyFiles {
  def copy(origin: File, destination: File): IO[Long] = ???

  def inputStream(file: File): Resource[IO, FileInputStream] =
    Resource.make {
      IO.blocking(new FileInputStream(file))
    } { stream =>
      IO.blocking(stream.close()).handleErrorWith(_ => IO.unit) // TODO any way to improve error handling?
    }

  def outputStream(file: File): Resource[IO, FileOutputStream] =
    Resource.make {
      IO.blocking(new FileOutputStream(file))
    } { stream =>
      IO.blocking(stream.close()).handleErrorWith(_ => IO.unit) // TODO any way to improve error handling?
    }


}
