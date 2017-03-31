package me.yangbajing.akkaaction.restapi

import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import me.yangbajing.akkaaction.restapi.book.BookRoute
import me.yangbajing.akkaaction.restapi.news.NewsRoute
import me.yangbajing.akkaaction.util.exception.MessageException
import org.json4s.JsonAST.{JInt, JObject, JString}

import scala.concurrent.ExecutionContextExecutor

/**
 * Api Route
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
object ApiRoute extends StrictLogging {
  val healthCheck =
    path("health_check") {
      get { ctx =>
        logger.debug(ctx.request.toString)
        ctx.complete(HttpEntity.Empty)
      }
    }

  import me.yangbajing.akkaaction.util.JsonSupport._

  /**
   * 自定义异常处理
   */
  val customExceptionHandler = ExceptionHandler {
    case e: MessageException =>
      extractRequest { req =>
        val msg =
          s"""\nmethod: ${req.method}
             |uri: ${req.uri}
             |headers:
             |\t${req.headers.mkString("\n\t")}
             |$e""".stripMargin
        if (e.errcode > 500) logger.error(msg, e)
        else logger.warn(msg)

        complete(
          StatusCodes.getForKey(e.errcode) getOrElse StatusCodes.InternalServerError,
          Map("errcode" -> e.errcode, "errmsg" -> e.errmsg))
      }
    case e: Exception =>
      extractRequest { req =>
        logger.error(req.toString, e)
        complete(
          StatusCodes.InternalServerError,
          Map("errcode" -> 500, "errmsg" -> e.getLocalizedMessage))
      }
  }

  def apply(props: ContextProps)(implicit ec: ExecutionContextExecutor, mat: Materializer) =
    handleExceptions(customExceptionHandler) {
      pathPrefix("api") {
        healthCheck ~
          NewsRoute(props) ~
          BookRoute(props)
      }
    }

}
