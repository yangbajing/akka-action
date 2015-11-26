package me.yangbajing.akkaaction.restapi.book

/**
 * BookContextProps
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
trait BookContextProps {
  val bookService = new BookService()
}
