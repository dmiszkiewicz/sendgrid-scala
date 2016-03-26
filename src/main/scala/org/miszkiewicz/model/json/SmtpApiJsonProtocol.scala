package org.miszkiewicz.model.json

import org.miszkiewicz.model.smtpapi.{SendAt, SendEachAt, SmtpApi}
import spray.json.{DefaultJsonProtocol, JsNumber, JsObject, JsValue, RootJsonFormat}
import spray.json._

object SmtpApiJsonProtocol extends DefaultJsonProtocol {

  import FilterJsonProtocol._
  import SendTimeJsonProtocol._

  implicit object SmtpApiJsonFormat extends RootJsonFormat[SmtpApi] {
    def write(c: SmtpApi) = JsObject(
      "asm_group_id" -> c.asmGroupId.map(JsNumber(_)).getOrElse(JsNull),
      "to" -> c.recipient.toJson,
      "unique_args" -> c.uniqueArguments.toJson,
      "category" -> c.categories.toJson,
      "section" -> c.sections.toJson,
      "send_at" -> c.scheduled.toJson,
      "sub" -> c.substitutions.toJson,
      "filters" -> c.filters.toJson
    )

    def read(value: JsValue) = ???
  }

}
