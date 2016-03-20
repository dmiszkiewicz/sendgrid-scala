package com.example

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpEntity, HttpResponse}
import org.json.JSONObject

import scala.concurrent.{ExecutionContext, Future}

class SendGrid {

  import SendGrid._

  private val url: String = "https://api.sendgrid.com"
  private val endpoint: String = "/api/mail.send.json"
  private var username: Option[String] = None
  private var password: String = _
  private val client: CloseableHttpClient = HttpClientBuilder.create.setUserAgent(USER_AGENT).build

  def this(username: String, password: String) {
    this()
    this.username = Some(username)
    this.password = password
  }

  def this(apiKey: String) {
    this()
    this.password = apiKey
  }

  def buildBody(email: Email): HttpEntity = {
    val builder: MultipartEntityBuilder = MultipartEntityBuilder.create
    username.foreach { un =>
      builder.addTextBody(APIUSER, un)
      builder.addTextBody(APIKEY, password)
    }
    if (email.to.isEmpty) {
      email.from.foreach { from =>
        builder.addTextBody(PARAM_TO, from, ContentType.create(TEXT_PLAIN, UTF_8))
      }
    }
    email.to.foreach(to => builder.addTextBody(PARAM_TO, to, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.toname.foreach(toName => builder.addTextBody(PARAM_TONAME, toName, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.cc.foreach(cc => builder.addTextBody(PARAM_CC, cc, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.bcc.foreach(bcc => builder.addTextBody(PARAM_BCC, bcc, ContentType.create(TEXT_PLAIN, UTF_8)))
    email.attachments.foreach { attachment =>
      builder.addBinaryBody(String.format(PARAM_FILES, attachment._1), attachment._2)
    }
    email.contents.foreach { content =>
      builder.addTextBody(String.format(PARAM_CONTENTS, content._1), content._2)
    }
    if (email.headers.nonEmpty)
      builder.addTextBody(PARAM_HEADERS, new JSONObject(email.headers).toString, ContentType.create(TEXT_PLAIN, UTF_8))
    email.from.map { from =>
      builder.addTextBody(PARAM_FROM, from, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.fromName.map { fromName =>
      builder.addTextBody(PARAM_FROMNAME, fromName, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.replyTo.map { replyTo =>
      builder.addTextBody(PARAM_REPLYTO, replyTo, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.subject.map { subject =>
      builder.addTextBody(PARAM_SUBJECT, subject, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.html.map { html =>
      builder.addTextBody(PARAM_HTML, html, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    email.text.map { text =>
      builder.addTextBody(PARAM_TEXT, text, ContentType.create(TEXT_PLAIN, UTF_8))
    }
    val tmpString: String = email.smtpapi.jsonString()
    if (tmpString != "{}")
      builder.addTextBody(PARAM_XSMTPAPI, tmpString, ContentType.create(TEXT_PLAIN, UTF_8))
    builder.build
  }

  def send(email: Email)(implicit ec: ExecutionContext): Future[Response] = {
    val httpPost: HttpPost = new HttpPost(url + endpoint)
    httpPost.setEntity(buildBody(email))
    username.foreach { un =>
      httpPost.setHeader(AUTHORIZATION, "Bearer " + password)
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
  val USER_AGENT: String = "sendgrid/" + VERSION + ";java"
  val PARAM_TO: String = "to[]"
  val PARAM_TONAME: String = "toname[]"
  val PARAM_CC: String = "cc[]"
  val PARAM_BCC: String = "bcc[]"
  val PARAM_FROM: String = "from"
  val PARAM_FROMNAME: String = "fromname"
  val PARAM_REPLYTO: String = "replyto"
  val PARAM_SUBJECT: String = "subject"
  val PARAM_HTML: String = "html"
  val PARAM_TEXT: String = "text"
  val PARAM_FILES: String = "files[%s]"
  val PARAM_CONTENTS: String = "content[%s]"
  val PARAM_XSMTPAPI: String = "x-smtpapi"
  val PARAM_HEADERS: String = "headers"
  val TEXT_PLAIN: String = "text/plain"
  val UTF_8: String = "UTF-8"
  val AUTHORIZATION: String = "Authorization"
  val APIUSER = "api_user"
  val APIKEY = "api_key"
}