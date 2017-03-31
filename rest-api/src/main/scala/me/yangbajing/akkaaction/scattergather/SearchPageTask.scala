package me.yangbajing.akkaaction.scattergather

import akka.actor.{Props, ActorRef}
import me.yangbajing.akkaaction.scattergather.SearchPageTask.SearchPage
import me.yangbajing.akkaaction.scattergather.model.{GetNewsItem, NewsItem}
import me.yangbajing.akkaaction.util.{TimeUtils, MetricActor}

import scala.util.Random
import scala.concurrent.duration._

/**
 * News Scatter 分散任务
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-25.
 */
class SearchPageTask(taskRef: ActorRef) extends MetricActor {
  override def metricReceive: Receive = {
    case SearchPage(key) =>
      // XXX 模拟抓取新闻时间
      TimeUtils.sleep(Random.nextInt(25).seconds)

      val item = NewsItem(
        "http://newssite/news/" + self.path.name,
        "测试新闻" + self.path.name,
        self.path.name,
        TimeUtils.now().toString,
        "内容简介", "新闻正文")

      taskRef ! GetNewsItem(item)
      context.stop(self)
  }
}

object SearchPageTask {

  case class SearchPage(key: String)

  def props(taskRef: ActorRef) = Props(new SearchPageTask(taskRef))
}
