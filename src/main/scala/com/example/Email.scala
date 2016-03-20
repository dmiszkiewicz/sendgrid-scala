package com.example

import java.io.InputStream

import com.sendgrid.smtpapi.SMTPAPI

case class Email(smtpapi: SMTPAPI = new SMTPAPI(),
                 from: Option[String] = None,
                 fromName: Option[String] = None,
                 replyTo: Option[String] = None,
                 subject: Option[String] = None,
                 text: Option[String] = None,
                 html: Option[String] = None,
                 bcc: Seq[String] = Seq(),
                 to: Seq[String] = Seq(),
                 toname: Seq[String] = Seq(),
                 cc: Seq[String] = Seq(),
                 attachments: Map[String, InputStream] = Map(),
                 contents: Map[String, String] = Map(),
                 headers: Map[String, String] = Map())

