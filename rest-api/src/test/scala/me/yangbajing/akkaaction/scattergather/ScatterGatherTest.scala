package me.yangbajing.akkaaction.scattergather

import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging
import me.yangbajing.akkaaction.scattergather.model.{NewsResult, StartFetchNews}
import me.yangbajing.akkaaction.util.TimeUtils
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpec}

import scala.concurrent.duration._

/**
 * ScatterGatherTest
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
class ScatterGatherTest extends WordSpec with MustMatchers with BeforeAndAfterAll with StrictLogging {

  import me.yangbajing.akkaaction.util.SystemUtils._
  import system.dispatcher

  // 设置 akka ask (?) 超时值
  implicit val timeout = Timeout(30.seconds)

  override protected def afterAll(): Unit = {
    TimeUtils.sleep(30.seconds)
    system.shutdown()
    system.awaitTermination()
    logger.info("Test done....")
  }

  "ScatterGatherTest" must {
    "await" in {
      // 15.seconds，设置15秒后无论任务是否结束都得返回数据。若任务还未结束，则返回已获取数据
      val newsTask = system.actorOf(NewsTask.props("重庆誉存信用管理有限公司", 15.seconds), "news-task")

      val getNewsFuture = (newsTask ? StartFetchNews).mapTo[NewsResult]

      getNewsFuture.onSuccess { case newsResult =>
        newsResult.news.foreach(println)
        println(newsResult.news.size)

        newsResult.news must not be empty
      }
    }
  }

}
