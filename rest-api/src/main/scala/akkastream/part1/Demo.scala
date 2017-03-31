package akkastream.part1

import akka.NotUsed

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-07-15.
  */
object Demo extends App {

  import akka.actor.ActorSystem
  import akka.stream._
  import akka.stream.scaladsl._

  val system = ActorSystem("LifecycleDemo")

  import system.dispatcher

  implicit val materializer = ActorMaterializer.create(system)

  println(Thread.currentThread().getName)

  //  Source.single("Hello")
  //    .map(_ + " Stream World!")
  //    .to(Sink.foreach(s ⇒ println(Thread.currentThread().getName + " " + s)))
  //    .run()
  //  println("running")
  //  Thread.sleep(1000)
  //  system.terminate()

  //  val completion = Source.single("Hello Stream World!\n")
  //    .map { s => println(Thread.currentThread().getName + " " + s); s }
  //    .map { s => println(Thread.currentThread().getName + " " + s); s }
  //    .runWith(Sink.foreach(s => println(Thread.currentThread().getName + " " + s)))

  def processingStage(name: String): Flow[String, String, NotUsed] =
    Flow[String].map { s =>
      println(name + " started processing " + s + " on thread " + Thread.currentThread().getName)
      Thread.sleep(100)
      println(name + " finished processing " + s)
      name + " " + s
    }

  val completion = Source(List("Hello", "Streams", "World!"))
    .via(processingStage("A")).async
    .via(processingStage("B")).async
    .via(processingStage("C")).async
    .runWith(Sink.foreach(s => println("Got output " + s)))

  completion.onComplete(_ => system.terminate())
}

class Message[+T](var cmd: String, var data: T)

trait ICodec[-T <: Message[_]] {
  def encode(msg: T)
}
