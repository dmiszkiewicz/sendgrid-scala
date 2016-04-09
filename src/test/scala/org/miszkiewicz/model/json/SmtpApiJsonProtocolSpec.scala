package org.miszkiewicz.model.json

import org.miszkiewicz.model.smtpapi._
import org.specs2.mutable.Specification
import spray.json._


class SmtpApiJsonProtocolSpec extends Specification {

  import FilterJsonProtocol._

  val result =
    """{
    "to":[
    "ben@sendgrid.com",
    "joe@sendgrid.com"
    ],
    "sub":{
      "%name%":[
      "Ben",
      "Joe"
      ],
      "%role%":[
      "%sellerSection%",
      "%buyerSection%"
      ]
    },
    "section":{
      "%sellerSection%":"Seller information for: %name%",
      "%buyerSection%":"Buyer information for: %name%"
    },
    "category":[
    "Orders"
    ],
    "unique_args":{
      "orderNumber":"12345",
      "eventID":"6789"
    },
    "filters":{
      "footer":{
      "settings":{
      "enable":1,
      "text/plain":"Thank you for your business"
    }
    }
    },
    "send_at":1409348513
  }"""

  import SmtpApiJsonProtocol._

  "FilterJsonProtocol" should {
    "deserialize filter to json" in {
      val smtpApi = SmtpApi(
        recipient = Seq("ben@sendgrid.com", "joe@sendgrid.com"),
        substitutions = Map("%name%" -> Seq("Ben", "Joe"), "%role%" -> Seq("%sellerSection%", "%buyerSection%")),
        sections = Map("%sellerSection%" -> "Seller information for: %name%",
          "%buyerSection%" -> "Buyer information for: %name%"),
        categories = Seq("Orders"),
        uniqueArguments = Map("orderNumber" -> "12345", "eventID" -> "6789"),
        filters = Seq(Footer(true, textPlain = Some("Thank you for your business"))),
        scheduled = Some(SendAt(1409348513))
      )
      smtpApi.toJson shouldEqual result.parseJson
    }
  }
}