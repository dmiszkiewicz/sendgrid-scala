package org.miszkiewicz.model.smtpapi

//TODO batch_id
case class SmtpApi(recipient: Seq[String] = Seq(), // optionally including the display name. to
                   uniqueArguments: Map[String, String] = Map(), //unique_args
                   categories: Seq[String] = Seq(), //up to 10 category
                   sections: Map[String, String] = Map(), //section
                   asmGroupId: Option[Int] = None, //asm_group_id
                   scheduled: Option[SendTime] = None, //send_at
                   substitutions: Map[String, Seq[String]] = Map(), //sub
                   filters: Seq[Filter] = Seq())