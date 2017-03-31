package me.yangbajing.akkaaction.util

import java.time.{LocalDate, LocalDateTime}

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.{HttpCharsets, MediaTypes}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import akka.stream.Materializer
import org.json4s.JsonAST.{JNull, JString}
import org.json4s.jackson.Serialization
import org.json4s.{CustomSerializer, DefaultFormats, Formats}

/**
  * Json Support
  * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-26.
  */
trait JsonSupport {
  implicit val jsonFormats: Formats = DefaultFormats + new LocalDateTimeSerializer + new LocalDateSerializer
  implicit val jsonSerialization = Serialization

  implicit def json4sUnmarshaller[A: Manifest](implicit mat: Materializer): FromEntityUnmarshaller[A] =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(MediaTypes.`application/json`)
      .mapWithCharset { (data, charset) =>
        val input = if (charset == HttpCharsets.`UTF-8`) data.utf8String else data.decodeString(charset.nioCharset().name)
        jsonSerialization.read(input)
      }

  implicit def json4sMarshaller[A <: AnyRef]: ToEntityMarshaller[A] =
    Marshaller.StringMarshaller.wrap(MediaTypes.`application/json`)(v => jsonSerialization.write[A](v))

}

object JsonSupport extends JsonSupport

class LocalDateTimeSerializer extends CustomSerializer[LocalDateTime](format =>
  ( {
    case JString(s) => LocalDateTime.parse(s, TimeUtils.formatterDateTime)
    case JNull => null
  }, {
    case d: LocalDateTime => JString(TimeUtils.formatterDateTime.format(d))
  })
)

class LocalDateSerializer extends CustomSerializer[LocalDate](format =>
  ( {
    case JString(s) => LocalDate.parse(s, TimeUtils.formatterDate)
    case JNull => null
  }, {
    case d: LocalDate => JString(TimeUtils.formatterDate.format(d))
  })
)
