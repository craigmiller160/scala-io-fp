package io.craigmiller160.scala.iofp.function

import cats.Monoid

object Monoids {
  val stringMonoid: Monoid[String] = new Monoid[String] {
    override def empty: String = ""
    override def combine(x: String, y: String): String = if (x.isEmpty) y else s"$x\n$y"
  }
}
