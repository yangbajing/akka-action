package me.yangbajing.akkaaction.scattergather

import akka.pattern.ask
import akka.util.Timeout
import me.yangbajing.akkaaction.scattergather.model.{NewsResult, StartFetchNews}
import me.yangbajing.akkaaction.util.TimeUtils

import scala.concurrent.duration._

/**
 * 分散，聚合示例
 * http://yangbajing.me/2015/11/25/akka实战：分散与聚合/
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-25.
 */
object ScatterGatherMain extends App {

  import me.yangbajing.akkaaction.util.SystemUtils._
  import system.dispatcher

  // 设置 akka ask (?) 超时值
  implicit val timeout = Timeout(30.seconds)

  // 15.seconds，设置15秒后无论任务是否结束都得返回数据。若任务还未结束，则返回已获取数据
  val newsTask = system.actorOf(NewsTask.props("重庆誉存信用管理有限公司", 15.seconds), "news-task")

  val getNewsFuture = (newsTask ? StartFetchNews).mapTo[NewsResult]

  getNewsFuture.onSuccess { case newsResult =>
    newsResult.news.foreach(println)
    println(newsResult.news.size)
  }

  TimeUtils.sleep(timeout.duration)
  system.shutdown()
  system.awaitTermination()
}
