package starter.akka.http.boot

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{HttpApp, Route}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2017-04-01.
  */
class WebServer extends HttpApp {
  def route: Route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }

  def traditionRoute: Route = {
    val resp = complete("result")
    val routePath = path("hello")
    routePath.apply(get(resp))
  }

}

object StartBoot {

  def main(args: Array[String]): Unit = {
    val server = new WebServer
    server.startServer("0.0.0.0", 8888)
  }

}
