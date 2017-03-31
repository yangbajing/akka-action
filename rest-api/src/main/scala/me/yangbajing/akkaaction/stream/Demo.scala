package me.yangbajing.akkaaction.stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Demo
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-27.
 */
object Demo extends App {
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  val source = Source(1 to 10)
  val sink = Sink.fold[Int, Int](0)(_ + _)

  val runnable = source.toMat(sink)(Keep.right)

  val sum = runnable.run()

  val result = Await.result(sum, 10.seconds)
  println(s"result: $result")
}
