package me.yangbajing.akkaaction.util

import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration.Duration

/**
 * Time Utils
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-25.
 */
object TimeUtils extends StrictLogging {
  def now() = LocalDateTime.now()

  def sleep(duration: Duration): Unit = {
    logger.debug(s"sleep $duration")
    TimeUnit.MILLISECONDS.sleep(duration.toMillis)
  }

}
