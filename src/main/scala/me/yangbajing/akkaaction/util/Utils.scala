package me.yangbajing.akkaaction.util

import java.lang.management.ManagementFactory
import java.nio.charset.Charset

/**
 * Utils
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
object Utils {
  val CHARSET = Charset.forName("UTF-8")

  def getPid = ManagementFactory.getRuntimeMXBean.getName.split('@')(0)

}
