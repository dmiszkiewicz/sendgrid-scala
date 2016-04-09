package org.miszkiewicz.model.json

import org.miszkiewicz.model.smtpapi._
import spray.json._

import scala.collection.mutable
import scala.language.implicitConversions

object FilterJsonProtocol extends DefaultJsonProtocol {

  implicit def bool2int(b: Boolean): Int = if (b) 1 else 0

  implicit object BCCJsonFormat extends RootJsonFormat[BCC] {
    def write(c: BCC) = JsObject(
      "enable" -> JsNumber(c.enable),
      "email" -> JsString(c.email)
    )

    def read(value: JsValue) = ???
  }

  implicit object BypassListManagementJsonFormat extends RootJsonFormat[BypassListManagement] {
    def write(c: BypassListManagement) = JsObject(
      "enable" -> JsNumber(c.enable)
    )

    def read(value: JsValue) = ???
  }

  implicit object ClickTrackJsonFormat extends RootJsonFormat[ClickTrack] {
    def write(c: ClickTrack) = JsObject(
      "enable" -> JsNumber(c.enable)
    )

    def read(value: JsValue) = ???
  }

  implicit object GravatarJsonFormat extends RootJsonFormat[Gravatar] {
    def write(c: Gravatar) = JsObject(
      "enable" -> JsNumber(c.enable)
    )

    def read(value: JsValue) = ???
  }

  implicit object dkimJsonFormat extends RootJsonFormat[DKIM] {
    def write(c: DKIM) = JsObject(
      "use_from" -> JsNumber(c.useFrom),
      "domain" -> JsString(c.domain)
    )

    def read(value: JsValue) = ???
  }

  implicit object OpenTrackJsonFormat extends RootJsonFormat[OpenTrack] {
    def write(c: OpenTrack) = JsObject(
      "enable" -> JsNumber(c.enable),
      "replace" -> JsString(c.replace)
    )

    def read(value: JsValue) = ???
  }

  implicit object FooterJsonFormat extends RootJsonFormat[Footer] {
    def write(c: Footer) = {
      val fields: mutable.MutableList[JsField] = mutable.MutableList("enable" -> JsNumber(c.enable))
      c.textHtml.foreach(textHtml => fields += ("text/html" -> JsString(textHtml)))
      c.textPlain.foreach(textPlain => fields += ("text/plain" -> JsString(textPlain)))
      JsObject(fields: _*)
    }

    def read(value: JsValue) = ???
  }

  implicit object SubscriptionTrackJsonFormat extends RootJsonFormat[SubscriptionTrack] {
    def write(c: SubscriptionTrack) = JsObject(
      "enable" -> JsNumber(c.enable),
      "text/html" -> JsString(c.textHtml),
      "text/plain" -> JsString(c.textPlain),
      "replace" -> JsString(c.replace)
    )

    def read(value: JsValue) = ???
  }

  implicit object TemplatesJsonFormat extends RootJsonFormat[Templates] {
    def write(c: Templates) = JsObject(
      "enable" -> JsNumber(c.enable),
      "template_id" -> JsString(c.templateId)
    )

    def read(value: JsValue) = ???
  }

  implicit object TemplateJsonFormat extends RootJsonFormat[Template] {
    def write(c: Template) = JsObject(
      "enable" -> JsNumber(c.enable),
      "text/html" -> JsString(c.textHtml)
    )

    def read(value: JsValue) = ???
  }

  implicit object GanalyticsJsonFormat extends RootJsonFormat[Ganalytics] {
    def write(c: Ganalytics) = JsObject(
      "enable" -> JsNumber(c.enable),
      "utm_campaign" -> JsString(c.utmCampaign),
      "utm_content" -> JsString(c.utmContent),
      "utm_medium" -> JsString(c.utmMedium),
      "utm_source" -> JsString(c.utmSource),
      "utm_term" -> JsString(c.utmTerm)
    )

    def read(value: JsValue) = ???
  }

  implicit object SpamCheckJsonFormat extends RootJsonFormat[SpamCheck] {
    def write(c: SpamCheck) = JsObject(
      "enable" -> JsNumber(c.enable),
      "maxscore" -> JsNumber(c.maxScore),
      "url" -> JsString(c.url)
    )

    def read(value: JsValue) = ???
  }

  implicit object FilterJsonFormat extends RootJsonFormat[Filter] {
    def write(c: Filter) = JsObject("settings" -> (c match {
        case f: BCC => f.toJson
        case f: BypassListManagement => f.toJson
        case f: ClickTrack => f.toJson
        case f: DKIM => f.toJson
        case f: Ganalytics => f.toJson
        case f: Gravatar => f.toJson
        case f: OpenTrack => f.toJson
        case f: SpamCheck => f.toJson
        case f: SubscriptionTrack => f.toJson
        case f: Template => f.toJson
        case f: Templates => f.toJson
        case f: Footer => f.toJson
      }))

    def read(value: JsValue) = ???
  }

}
