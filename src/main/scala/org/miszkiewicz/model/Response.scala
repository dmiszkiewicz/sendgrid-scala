package org.miszkiewicz.model

case class Response(code: Int, message: String) {
  val success = code == 200
}