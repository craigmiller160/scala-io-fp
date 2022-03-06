package io.craigmiller160.scala.iofp

import cats.effect._
import cats._
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import cats.implicits._
import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream}

object CopyFiles extends IOApp {
  private implicit def unsafeLogger[F[_]: Sync] = Slf4jLogger.getLogger[F]

  override def run(args: List[String]): IO[ExitCode] =
    copy[IO](new File("./build222.sbt"), new File("./build2.sbt"))
      .recoverWith(ex =>
        Logger[IO].error(ex)("Error copying file")
          .map(_ => -1)
      )
      .flatMap(count => IO.println(s"Transfer Count: $count"))
      .as(ExitCode.Success)

  def copy[F[_]: Sync](origin: File, destination: File): F[Long] =
    inputOutputStream(origin, destination).use {
      case (in, out) => transfer(in, out)
    }

  def transfer[F[_]: Sync](inStream: FileInputStream, outStream: FileOutputStream): F[Long] =
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

  def inputStream[F[_]: Sync](file: File): Resource[F, FileInputStream] =
    Resource.make {
      Sync[F].blocking(new FileInputStream(file))
    } { stream =>
      Sync[F].blocking(stream.close()).handleErrorWith(ex =>
        Logger[F].error(ex)("Error closing output stream")
          .flatMap(_ => Sync[F].unit)
      )
    }

  def outputStream[F[_]: Sync](file: File): Resource[F, FileOutputStream] =
    Resource.make {
      Sync[F].blocking(new FileOutputStream(file))
    } { stream =>
      Sync[F].blocking(stream.close()).handleErrorWith(ex =>
        Logger[F].error(ex)("Error closing output stream")
          .flatMap(_ => Sync[F].unit)
      )
    }

  def inputOutputStream[F[_]: Sync](inFile: File, outFile: File): Resource[F, (FileInputStream, FileOutputStream)] =
    for {
      inStream <- inputStream(inFile)
      outStream <- outputStream(outFile)
    } yield (inStream, outStream)
}
