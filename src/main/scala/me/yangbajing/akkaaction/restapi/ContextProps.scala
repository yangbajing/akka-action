package me.yangbajing.akkaaction.restapi

import me.yangbajing.akkaaction.restapi.book.BookContextProps
import me.yangbajing.akkaaction.restapi.news.NewsContextProps

/**
 * Context Props
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
class ContextProps
  extends NewsContextProps
  with BookContextProps {

}
