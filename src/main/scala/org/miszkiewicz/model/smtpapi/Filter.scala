package org.miszkiewicz.model.smtpapi

sealed trait Filter {
  def name: String
}

case class BCC(enable: Boolean, email: String) extends Filter {
  override val name: String = "bcc"
}

case class BypassListManagement(enable: Boolean) extends Filter {
  override val name: String = "bypass_list_management"
}

case class ClickTrack(enable: Boolean) extends Filter {
  override val name: String = "clicktrack"
}

case class Gravatar(enable: Boolean) extends Filter {
  override val name: String = "gravatar"
}

//case class dkim(use_from: Boolean, domain: String) extends Filter {
case class DKIM(useFrom: Boolean, domain: String) extends Filter {
  override val name: String = "dkim"
}

case class OpenTrack(enable: Boolean, replace: String) extends Filter {
  override val name: String = "opentrack"
}

//case class footer(enable: Boolean, `text/html`: String, `text/plain`: String) extends Filter {
case class Footer(enable: Boolean, textHtml: Option[String] = None, textPlain: Option[String] = None) extends Filter {
  override val name: String = "footer"
}

//case class subscriptiontrack(enable: Boolean, `text/html`: String, `text/plain`: String, replace: String) extends Filter {
case class SubscriptionTrack(enable: Boolean, textHtml: String, textPlain: String, replace: String) extends Filter {
  override val name: String = "subscriptiontrack"
}

//case class templates(enable: Boolean, template_id: String) extends Filter{
case class Templates(enable: Boolean, templateId: String) extends Filter {
  override val name: String = "templates"
}

//case class template(enable: Boolean, `text/html`: String) extends Filter{
case class Template(enable: Boolean, textHtml: String) extends Filter {
  override val name: String = "template"
}

/*case class ganalytics(enable: Boolean,
                      utm_source: String,
                      utm_medium: String,
                      utm_term: String,
                      utm_content: String,
                      utm_campaign: String) extends Filter {*/

case class Ganalytics(enable: Boolean,
                      utmSource: String,
                      utmMedium: String,
                      utmTerm: String,
                      utmContent: String,
                      utmCampaign: String) extends Filter {
  override val name: String = "ganalytics"
}

case class SpamCheck(enable: Boolean, maxScore: Double, url: String) extends Filter {
  override val name: String = "spamcheck"
}
