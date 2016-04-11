name := """sendgrid-scala"""

organization := "org.miszkiewicz"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

val httpcomponentsVersion = "4.5.2"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % httpcomponentsVersion
libraryDependencies += "org.apache.httpcomponents" % "httpmime" % httpcomponentsVersion

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.2"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.7.2" % Test

scalacOptions in Test ++= Seq("-Yrangepos")

publishMavenStyle := true

pomIncludeRepository := { _ => false }

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <url>https://github.com/eigengo/sbt-raml-plugin</url>
    <licenses>
      <license>
        <name>The MIT License</name>
        <url>https://opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:dmiszkiewicz/sendgrid-scala.git</url>
      <connection>scm:git:git@github.com:dmiszkiewicz/sendgrid-scala.git</connection>
    </scm>
    <developers>
      <developer>
        <id>dmiszkiewicz</id>
        <name>Dominik Miszkiewicz</name>
        <url>http://www.miszkiewicz.org/</url>
      </developer>
    </developers>)