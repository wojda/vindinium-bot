name := "vindinium-bot"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.5.4",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.slf4j" % "slf4j-api" % "1.7.22",
  "ch.qos.logback" % "logback-parent" % "1.1.8",
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test
)
