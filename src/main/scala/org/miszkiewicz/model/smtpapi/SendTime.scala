package org.miszkiewicz.model.smtpapi

sealed trait SendTime{
  def name: String
}

case class SendAt(timestamp: Int) extends SendTime{
  val name = "send_at"
}

case class SendEachAt(timestamps: Seq[Int]) extends SendTime{
  val name = "send_each_at"
}
