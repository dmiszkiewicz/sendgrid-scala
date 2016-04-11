package org.miszkiewicz

import org.miszkiewicz.model._
import org.miszkiewicz.model.smtpapi.{SmtpApi, Templates}
import org.specs2.mutable.Specification

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class SendGridSpec extends Specification {

  val sendGrid: Option[SendGrid] = sys.props.get("sendgrid.test") map { s =>
    val (login, password) = splitToTuple(s)
    new SendGrid(UserCredentials(login, password))
  }

  "SendGrid" should {
    "send email" in {
      lazy val sendMail =
      {
        val smtpApi = SmtpApi(
          substitutions = Map("-link-" -> Seq("https://github.com/dmiszkiewicz")),
          filters = Seq(Templates(true, "e317f565-ee21-4f2d-85a1-b3eaf775896e"))
        )
        val sendGridEmail = Email(
          html = Some("Look at my GitHub: "),
          subject = Some("Look at my GitHub!"),
          from = Some("dominik223@gmail.com"),
          fromName = Some("Dominik Miszkiewicz"),
          to = Seq("dominik223@gmail.com"),
          smtpAPI = Some(smtpApi)
        )
        val response = Await.result(sendGrid.get.send(sendGridEmail), 20 seconds)
        response.code shouldEqual 200
      }
      sendGrid match {
        case Some(_) =>
          sendMail.toResult
        case None =>
          sendMail.pendingUntilFixed("No credentials found for SendGrid")
      }
    }
  }

  private def splitToTuple(s: String): (String, String) = {
    s.split(":") match {
      case Array(login: String, password: String) => (login, password)
      case _ => throw new Exception()
    }
  }
}
