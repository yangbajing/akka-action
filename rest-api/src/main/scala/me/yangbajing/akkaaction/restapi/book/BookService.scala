package me.yangbajing.akkaaction.restapi.book

import java.time.LocalDate

import me.yangbajing.akkaaction.util.exception.{MeConflictMessage, MeNotFoundMessage}

import scala.concurrent.{ExecutionContext, Future}

/**
 * BookService
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
class BookService() {
  def updateById(bookId: String, book: Book)(implicit ec: ExecutionContext): Future[Book] = Future {
    if (bookId != book.id)
      throw MeConflictMessage(s"${book.id} is invalid，the ID is expected to be $bookId")

    val newBooks = BookService.books.filterNot(_.id == bookId)
    if (newBooks.size == BookService.books.size)
      throw MeNotFoundMessage(s"$bookId not found")

    BookService.books ::= book
    book
  }

  def persist(book: Book)(implicit ec: ExecutionContext): Future[Book] = Future {
    if (BookService.books.exists(_.id == book.id))
      throw MeConflictMessage(s"${book.id} exsits")

    BookService.books ::= book
    book
  }

  def deleteById(bookId: String)(implicit ec: ExecutionContext): Future[String] = Future {
    val newBooks = BookService.books.filterNot(_.id == bookId)
    if (newBooks.size == BookService.books.size)
      throw MeNotFoundMessage(s"$bookId not found")

    BookService.books = newBooks
    bookId
  }

  def findOneById(bookId: String)(implicit ec: ExecutionContext): Future[Book] = Future {
    BookService.books.find(_.id == bookId).getOrElse(throw MeNotFoundMessage(s"$bookId not found"))
  }

}

object BookService {
  private var books = List(
    Book("aa", "aa-bb", "《Akka实战》", "杨景", LocalDate.now(), "Akka实例教程")
  )
}
