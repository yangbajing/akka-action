package me.yangbajing.akkaaction.restapi.book

import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

/**
 * Book Route
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
object BookRoute {

  import akka.http.scaladsl.server.Directives._
  import me.yangbajing.akkaaction.util.JsonSupport._

  def apply(props: BookContextProps)(implicit ec: ExecutionContextExecutor, mat: Materializer) =
    pathPrefix("book") {
      pathEnd {
        post {
          entity(as[Book]) { book =>
            onSuccess(props.bookService.persist(book)) { result =>
              complete(StatusCodes.Created, result)
            }
          }
        }
      } ~
        path(Segment) { bookId =>
          get {
            complete(props.bookService.findOneById(bookId))
          } ~
            put {
              entity(as[Book]) { book =>
                complete(props.bookService.updateById(bookId, book))
              }
            } ~
            delete {
              complete(props.bookService.deleteById(bookId).map(id => Map("id" -> id)))
            }
        }
    }

}
