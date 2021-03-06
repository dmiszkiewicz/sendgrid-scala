package org.miszkiewicz

import org.miszkiewicz.SendGrid._
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpEntity, HttpResponse}
import org.miszkiewicz.model._
import spray.json._

import scala.concurrent.{ExecutionContext, Future}


class SendGrid(credentials: SendGridCredentials,
               url: String = DEFULTSENDGRIDURL,
               endpoint: String = DEFULTSENDGRIDENDPOINT,
               client: CloseableHttpClient = HttpClientBuilder.create.setUserAgent(USER_AGENT).build) {

  import org.miszkiewicz.model.json.SmtpApiJsonProtocol._

  def buildBody(email: Email): HttpEntity = {
    val builder: MultipartEntityBuilder = MultipartEntityBuilder.create
    credentials match {
      case UserCredentials(user, password) =>
        builder.addTextBody(APIUSER, user)
        builder.addTextBody(APIKEY, password)
      case ak: ApiKey =>
    }
    if (email.to.isEmpty) {
      email.from.foreach { from =>
        builder.addTextBody(TO, from, ContentType.create(TEXT_PLAIN, UTF_8))
      }
    }
    email.to.foreach(to => builder.addTextBody(TO, to, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.toName.foreach(toName => builder.addTextBody(TONAME, toName, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.cc.foreach(cc => builder.addTextBody(CC, cc, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.bcc.foreach(bcc => builder.addTextBody(BCC, bcc, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.attachments.foreach { attachment =>
      builder.addBinaryBody(String.format(FILES, attachment._1), attachment._2)
    }
    email.contents.foreach { content =>
      builder.addTextBody(String.format(CONTENTS, content._1), content._2)
    }
    if (email.headers.nonEmpty)
      builder.addTextBody(HEADERS, email.headers.toJson.compactPrint, ContentType.create(TEXT_PLAIN, UTF_8))
    email.from.map { from =>
      builder.addTextBody(FROM, from, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.fromName.map { fromName =>
      builder.addTextBody(FROMNAME, fromName, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.replyTo.map { replyTo =>
      builder.addTextBody(REPLYTO, replyTo, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.subject.map { subject =>
      builder.addTextBody(SUBJECT, subject, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.html.map { html =>
      builder.addTextBody(HTML, html, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.text.map { text =>
      builder.addTextBody(TEXT, text, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.smtpAPI.foreach{smptApi =>
      val smptApiJson = smptApi.toJson.compactPrint
      builder.addTextBody(XSMTPAPI, escapeUnicode(smptApiJson), ContentType.create(TEXT_PLAIN, UTF_8))}
    builder.build
  }

  def send(email: Email)(implicit ec: ExecutionContext): Future[Response] = {
    val httpPost: HttpPost = new HttpPost(url + endpoint)
    httpPost.setEntity(buildBody(email))
    credentials match {
      case UserCredentials(user, password) =>
      case ApiKey(apiKey) =>
        httpPost.setHeader(AUTHORIZATION, "Bearer " + apiKey)

    }
    Future {
      val res: HttpResponse = this.client.execute(httpPost)
      Response(res.getStatusLine.getStatusCode, EntityUtils.toString(res.getEntity))
    }.recover {
      case e => throw new SendGridException(e)
    }
  }
}

object SendGrid {
  val VERSION: String = "2.2.2"
  val USER_AGENT: String = "sendgrid/" + VERSION + ";scala"
  val TO: String = "to[]"
  val TONAME: String = "toname[]"
  val CC: String = "cc[]"
  val BCC: String = "bcc[]"
  val FROM: String = "from"
  val FROMNAME: String = "fromname"
  val REPLYTO: String = "replyto"
  val SUBJECT: String = "subject"
  val HTML: String = "html"
  val TEXT: String = "text"
  val FILES: String = "files[%s]"
  val CONTENTS: String = "content[%s]"
  val XSMTPAPI: String = "x-smtpapi"
  val HEADERS: String = "headers"
  val TEXT_PLAIN: String = "text/plain"
  val UTF_8: String = "UTF-8"
  val AUTHORIZATION: String = "Authorization"
  val APIUSER = "api_user"
  val APIKEY = "api_key"
  val DEFULTSENDGRIDURL = "https://api.sendgrid.com"
  val DEFULTSENDGRIDENDPOINT = "/api/mail.send.json"

  /**
    * Copied from sendgrid-java project
    * @param input
    * @return
    */
  private def escapeUnicode(input: String): String = {
    val sb: StringBuilder = new StringBuilder
    val codePointArray = input.map(c => c.toInt)
    codePointArray.foreach{ i =>
      if (i > 65535) {
        val hi: Int = (i - 0x10000) / 0x400 + 0xD800
        val lo: Int = (i - 0x10000) % 0x400 + 0xDC00
        sb.append("\\u%04x\\u%04x".format(hi, lo))
      }
      else if (i > 127) {
        sb.append("\\u%04x".format(i))
      }
      else {
        sb.append("%c".format(i))
      }
    }
    sb.toString
  }
}