package org.miszkiewicz.model.smtpapi

//TODO batch_id
case class SmtpApi(recipient: Seq[String] = Seq(), // optionally including the display name. to
                   uniqueArguments: Map[String, String] = Map(), //unique_args
                   categories: Seq[String] = Seq(), //up to 10 category
                   sections: Map[String, String] = Map(), //section
                   asmGroupId: Option[Int] = None, //asm_group_id
                   scheduled: Option[SendTime] =None, //send_at
                   substitutions: Map[String, Seq[String]] = Map(), //sub
                   filters: Seq[Filter] =Seq()) {


/*  private def escapeUnicode(input: String): String = {
    val sb: StringBuilder = new StringBuilder
    val codePointArray = input.map(c => c.toInt)
    for (i <- codePointArray.indices) {
      {
        if (codePointArray(i) > 65535) {
          val hi: Int = (codePointArray(i) - 0x10000) / 0x400 + 0xD800
          val lo: Int = (codePointArray(i) - 0x10000) % 0x400 + 0xDC00
          sb.append(String.format("\\u%04x\\u%04x", hi, lo))
        }
        else if (codePointArray(i) > 127) {
          sb.append(String.format("\\u%04x", codePointArray(i)))
        }
        else {
          sb.append(String.format("%c", codePointArray(i)))
        }
      }
    }
    sb.toString
  }*/
}