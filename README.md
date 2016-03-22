# sendgrid-scala
[![Build Status](https://travis-ci.org/dmiszkiewicz/sendgrid-scala.svg?branch=master)](https://travis-ci.org/dmiszkiewicz/sendgrid-scala)

sengrid-scala is a SendGrid client, for now it's just a copy of sendgrid-java so the code is not very good. But I want to make it more in scala style by using akka http for sending requests and make API more friendly.

# Installation

If you use SBT, you can include sendgrid-scala in your project with

```
resolvers += "SonaType" at "https://oss.sonatype.org/content/groups/public"
libraryDependencies += "org.miszkiewicz" %% "sendgrid-scala" % "0.1-SNAPSHOT"
```