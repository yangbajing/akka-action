package me.yangbajing.akkaaction.restapi.news

import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

/**
 * News Route
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
object NewsRoute {

  import akka.http.scaladsl.server.Directives._
  import me.yangbajing.akkaaction.util.JsonSupport._

  def apply(props: NewsContextProps)(implicit ec: ExecutionContextExecutor, mat: Materializer) =
    path("news") {
      get {
        parameter('key) { key =>
          complete(props.newsService.findByKey(key))
        }
      }
    }

}
