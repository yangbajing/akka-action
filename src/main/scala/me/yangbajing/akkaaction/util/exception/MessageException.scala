package me.yangbajing.akkaaction.util.exception

/**
 * Message Exception
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
 */
abstract class MessageException(val errmsg: String, val errcode: Int = -1) extends RuntimeException(errmsg)

case class MeNotFoundMessage(override val errmsg: String,
                             override val errcode: Int = 404) extends MessageException(errmsg, errcode)

case class MeConflictMessage(override val errmsg: String,
                            override val errcode: Int = 409) extends MessageException(errmsg, errcode)

case class MeInternalServerMessage(override val errmsg: String,
                                   override val errcode: Int = 500) extends MessageException(errmsg, errcode)