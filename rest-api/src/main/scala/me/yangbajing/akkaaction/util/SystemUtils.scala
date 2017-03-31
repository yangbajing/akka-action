package me.yangbajing.akkaaction.util

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

/**
 * System Utils
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-25.
 */
object SystemUtils {
  implicit val system = ActorSystem("akka")
  implicit val materializer = ActorMaterializer()
}
