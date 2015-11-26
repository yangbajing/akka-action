package me.yangbajing.akkaaction.restapi

import java.nio.file.{Paths, Files}

import akka.http.scaladsl.Http
import com.typesafe.scalalogging.StrictLogging
import me.yangbajing.akkaaction.util.Utils

import scala.util.{Failure, Success}

/**
 * App
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
object App extends StrictLogging {

  import me.yangbajing.akkaaction.util.SystemUtils._
  import system.dispatcher

  def main(args: Array[String]): Unit = {
    Files.write(Paths.get("app.pid"), Utils.getPid.getBytes(Utils.CHARSET))

    val contextProps = new ContextProps

    val bindingFuture = Http().bindAndHandle(ApiRoute(contextProps), "0.0.0.0", 3333)

    bindingFuture.onComplete {
      case Success(binding) =>
        logger.info(binding.toString)
      case Failure(e) =>
        logger.error(e.getLocalizedMessage, e)
    }
  }

}
