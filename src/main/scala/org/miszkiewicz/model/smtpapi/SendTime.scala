package org.miszkiewicz.model.smtpapi

sealed trait SendTime

//case class SendAt(send_at: Int)
case class SendAt(timestamp: Int)

//case class SendEachAt(send_each_at: Seq[Int])
case class SendEachAt(timestamps: Seq[Int])
