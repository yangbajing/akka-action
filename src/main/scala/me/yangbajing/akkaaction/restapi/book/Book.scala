package me.yangbajing.akkaaction.restapi.book

import java.time.LocalDate

/**
 * Book
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
case class Book(id: String,
                isbn: String,
                title: String,
                author: String,
                date: LocalDate,
                description: String)
