package me.yangbajing.akkaaction.restapi.news

/**
 * NewsContextProps
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
trait NewsContextProps {
  val newsService = new NewsService()
}
