package org.miszkiewicz.model

import java.io.InputStream

import org.miszkiewicz.model.smtpapi.SmtpApi

case class Email(smtpAPI: Option[SmtpApi] = None,
                 from: Option[String] = None,
                 fromName: Option[String] = None,
                 replyTo: Option[String] = None,
                 subject: Option[String] = None,
                 text: Option[String] = None,
                 html: Option[String] = None,
                 bcc: Seq[String] = Seq(),
                 to: Seq[String] = Seq(),
                 toName: Seq[String] = Seq(),
                 cc: Seq[String] = Seq(),
                 attachments: Map[String, InputStream] = Map(),
                 contents: Map[String, String] = Map(),
                 headers: Map[String, String] = Map())

