package org.miszkiewicz.model.json

import org.miszkiewicz.model.smtpapi.{BCC, Filter}
import org.specs2.mutable.Specification
import spray.json._

class FilterJsonProtocolSpec extends Specification {

  import FilterJsonProtocol._

  "FilterJsonProtocol" should {
    "deserialize filter to json" in {
      val filter: Filter = BCC(true, "you@example.com")
      filter.toJson shouldEqual "{\"settings\":{\"enable\":1,\"email\":\"you@example.com\"}}".parseJson
    }
  }
}