package org.miszkiewicz.model

sealed trait SendGridCredentials

case class ApiKey(apiKey: String) extends SendGridCredentials

case class UserCredentials(login: String, password: String) extends SendGridCredentials
