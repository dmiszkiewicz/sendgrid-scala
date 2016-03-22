package org.miszkiewicz

sealed trait SendGridCredentials

case class ApiKey(apiKey: String) extends SendGridCredentials

case class UserPasswordCredentials(user: String, password: String) extends SendGridCredentials
