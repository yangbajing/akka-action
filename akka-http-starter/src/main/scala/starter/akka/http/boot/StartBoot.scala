package starter.akka.http.boot

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import starter.akka.http.route.Routes

import scala.util.{Failure, Success}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2017-04-01.
  */
object StartBoot {
  implicit val theSystem = ActorSystem("akka-http-starter")
  implicit val materializer = ActorMaterializer()
  implicit val ec = theSystem.dispatcher


  def main(args: Array[String]): Unit = {
    val bindingFuture = Http().bindAndHandle(
      handler = new Routes().route,
      interface = "0.0.0.0",
      port = 9999)

    bindingFuture.onComplete {
      case Success(binding) ⇒
        println(s"Bind success: $binding")
      case Failure(cause) ⇒
        cause.printStackTrace()
        System.exit(-1)
    }
  }

}
