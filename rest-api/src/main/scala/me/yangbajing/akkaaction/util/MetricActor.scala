package me.yangbajing.akkaaction.util

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging

/**
 * Metric Actor
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-25.
 */
trait MetricActor extends Actor with LazyLogging {
  final def receive = {
    case message =>
      logger.debug(s"${self.path} receive $message")
      metricReceive.applyOrElse(message, unhandled)
  }


  @throws[Exception](classOf[Exception])
  final override def preStart(): Unit = {
    logger.info(s"${self.path} preStart")
    MetricActor.incrementActiveActor()
    metricPreStart()
  }

  @throws[Exception](classOf[Exception])
  final override def postStop(): Unit = {
    metricPostStop()
    MetricActor.decrementActiveActor()
    logger.info(s"${self.path} postStop")
  }

  def metricReceive: Receive

  def metricPreStart(): Unit = ()

  def metricPostStop(): Unit = ()

}

object MetricActor extends LazyLogging {
  private val _activeActors = new AtomicInteger(0)

  def activeActors = _activeActors.get()

  def incrementActiveActor() = {
    _activeActors.incrementAndGet()
    logger.trace("incrementActiveActor after: " + _activeActors.get())
  }

  def decrementActiveActor() = {
    _activeActors.decrementAndGet()
    logger.trace("decrementActiveActor after: " + _activeActors.get())
  }
}