package me.yangbajing.akkaaction.scattergather

import akka.actor.{ActorRef, PoisonPill, Props}
import me.yangbajing.akkaaction.scattergather.SearchPageTask.SearchPage
import me.yangbajing.akkaaction.scattergather.model._
import me.yangbajing.akkaaction.util.{MetricActor, TimeUtils}

import scala.concurrent.duration._

/**
 * News Task
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-25.
 */
class NewsTask(key: String, doneDuration: FiniteDuration) extends MetricActor {

  import context.dispatcher

  @volatile
  private var _newses = List.empty[NewsItem]

  @volatile
  private var _receipt: ActorRef = null

  override def metricPostStop(): Unit = {
    // TODO 在此进行持久化操作，直接存库或把数据发到一个MQ
  }

  override def metricPreStart(): Unit = {
    context.system.scheduler.scheduleOnce(doneDuration, self, TaskDelay)
  }

  override def metricReceive: Receive = {
    case StartFetchNews =>
      _receipt = sender()
      (0 until NewsTask.TASK_SIZE).foreach { i =>
        context.actorOf(SearchPageTask.props(self), "scatter-" + i) ! SearchPage(key)
      }

    case GetNewsItem(newsItem) =>
      _newses ::= newsItem
      if (_newses.size == NewsTask.TASK_SIZE) {
        logger.debug(s"分散任务，${NewsTask.TASK_SIZE}个已全部完成")

        if (_receipt != null) {
          _receipt ! NewsResult(key, _newses)
          _receipt = null
        }
        self ! PoisonPill
      }

    case TaskDelay =>
      if (_receipt != null) {
        _receipt ! NewsResult(key, _newses)
        _receipt = null
      }
  }
}

object NewsTask {
  val TASK_SIZE = 5

  def props(key: String, delay: FiniteDuration) = Props(new NewsTask(key, delay))
}
