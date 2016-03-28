package org.miszkiewicz.model.json

import org.miszkiewicz.model.smtpapi.SendAt
import org.specs2.mutable.Specification
import spray.json._

class SendTimeJsonProtocolSpec extends Specification {

  import SendTimeJsonProtocol._

  "SendTimeJsonProtocol" should {
    "deserialize SendTime to json" in {
      val sendTime = SendAt(1)
      sendTime.toJson shouldEqual "{\"send_at\":1}".parseJson
    }
  }
}