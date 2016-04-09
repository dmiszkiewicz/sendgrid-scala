package org.miszkiewicz.model.json

import org.miszkiewicz.model.smtpapi.{SendAt, SendEachAt, SendTime}
import spray.json._

object SendTimeJsonProtocol extends DefaultJsonProtocol {

  implicit object SendTimeJsonFormat extends RootJsonFormat[SendTime] {
    def write(c: SendTime) = c match {
      case sa: SendAt => SendAtJsonFormat.write(sa)
      case sea: SendEachAt => SendEachAtJsonFormat.write(sea)
    }

    def read(value: JsValue) = ???
  }

  implicit object SendAtJsonFormat extends RootJsonFormat[SendAt] {
    def write(c: SendAt) = JsNumber(c.timestamp)

    def read(value: JsValue) = ???
  }

  implicit object SendEachAtJsonFormat extends RootJsonFormat[SendEachAt] {
    def write(c: SendEachAt) = c.timestamps.toJson

    def read(value: JsValue) = ???
  }

}
