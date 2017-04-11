package starter.akka.http.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import starter.akka.http.json.JacksonSupport._

case class PageInput(title: String, content: String)

class PageRoute {

  def route: Route =
    path("page") {
      post {
        entity(as[PageInput]) { pageInput =>
          complete(pageInput)
        }
      }
    }

}
