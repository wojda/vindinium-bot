name := "vindinium-bot"

lazy val commonSettings = Seq(
  organization := "org.danielwojda",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.1"
)

lazy val botScalaDependencies = Seq(
  "com.typesafe.play" %% "play-json" % "2.6.0-M6",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.slf4j" % "slf4j-api" % "1.7.22",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % Test
)

lazy val botScala = project.in(file("bot-scala"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= botScalaDependencies)

lazy val root = project.in(file(".")).aggregate(botScala)