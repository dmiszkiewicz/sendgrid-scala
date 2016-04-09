package org.miszkiewicz.model.json

import org.miszkiewicz.model.smtpapi.{SendAt, SendEachAt, SmtpApi}
import spray.json.{DefaultJsonProtocol, JsNumber, JsObject, JsValue, RootJsonFormat, _}

import scala.collection.mutable

object SmtpApiJsonProtocol extends DefaultJsonProtocol {

  import FilterJsonProtocol._
  import SendTimeJsonProtocol._

  implicit object SmtpApiJsonFormat extends RootJsonFormat[SmtpApi] {
    def write(c: SmtpApi) = {
      val fields: mutable.MutableList[JsField] =
        mutable.MutableList(
          "to" -> c.recipient.toJson,
          "unique_args" -> c.uniqueArguments.toJson,
          "category" -> c.categories.toJson,
          "section" -> c.sections.toJson,
          "sub" -> c.substitutions.toJson,
          "filters" -> JsObject(c.filters.map(filter => filter.name -> filter.toJson): _*))
      c.asmGroupId.foreach(asmGroupId => fields += ("asm_group_id" -> JsNumber(asmGroupId)))
      c.scheduled.foreach(scheduled => fields += (scheduled.name -> scheduled.toJson))
      JsObject(fields: _*)
    }

    def read(value: JsValue) = ???
  }

}
