package starter.akka.http.demo01

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{HttpApp, Route}

class WebServer extends HttpApp {

  def route: Route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
      path("book") {
        get {
          parameters('name.as[Option[String]], 'isbn.as[Option[String]], 'author.as[Option[String]]) {
            (maybeName, maybeIsbn, maybeAuthor) =>
              complete(s"name: $maybeName, isbn: $maybeIsbn, author: $maybeAuthor")
          }
        }
      }

  def traditionRoute: Route = {
    val resp = complete("result")
    val routePath = path("hello")
    routePath.apply(get(resp))
  }

}

object StartBoot01 {

  def main(args: Array[String]): Unit = {
    val server = new WebServer
    server.startServer("0.0.0.0", 8888)
  }

}
