package io.craigmiller160.scala.iofp

import cats.effect.{ExitCode, IO, IOApp, Resource, Sync}
import cats.implicits._

import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream}

object CopyFiles extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    copy(new File("./build.sbt"), new File("./build2.sbt"))
      .as(ExitCode.Success)

  def copy(origin: File, destination: File): IO[Long] =
    inputOutputStream(origin, destination).use {
      case (in, out) => transfer(in, out)
    }

  def transfer(inStream: FileInputStream, outStream: FileOutputStream): IO[Long] =
    transmit(inStream, outStream, new Array[Byte](1024 * 10), 0L)

  def transmit[F[_]: Sync](origin: InputStream, destination: OutputStream, buffer: Array[Byte], acc: Long): F[Long] =
    for {
      amount <- Sync[F].blocking(origin.read(buffer, 0, buffer.length))
      count <- if (amount > -1) {
        Sync[F].blocking(destination.write(buffer, 0, amount))
          .flatMap(_ => transmit(origin, destination, buffer, acc + amount))
      } else {
        Sync[F].pure(acc)
      }
    } yield count

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

  def inputOutputStream(inFile: File, outFile: File): Resource[IO, (FileInputStream, FileOutputStream)] =
    for {
      inStream <- inputStream(inFile)
      outStream <- outputStream(outFile)
    } yield (inStream, outStream)
}
