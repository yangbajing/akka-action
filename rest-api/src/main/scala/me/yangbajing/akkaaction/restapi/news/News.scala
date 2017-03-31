package me.yangbajing.akkaaction.restapi.news

import java.time.LocalDateTime

/**
 * News
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
case class News(key: String,
                url: String,
                title: String,
                author: String,
                time: LocalDateTime,
                summary: String,
                content: String)
