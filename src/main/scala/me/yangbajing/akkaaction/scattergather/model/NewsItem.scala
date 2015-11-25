package me.yangbajing.akkaaction.scattergather.model

import scala.concurrent.duration._

/**
 * 新闻详情
 * @param url 网页链接
 * @param title 新闻标题
 * @param author 新闻作者
 * @param time 发布时间（yyyy-MM-dd HH:mm:ss）
 * @param summary 摘要
 * @param content 正文
 */
case class NewsItem(url: String, title: String, author: String, time: String, summary: String, content: String)

/**
 * 获取新闻
 */
case object StartFetchNews

case class NewsResult(key: String, news: Seq[NewsItem])

case class GetNewsItem(item: NewsItem)

case object TaskDelay
