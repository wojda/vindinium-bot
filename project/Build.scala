import sbt._, Keys._

object VindiniumBot extends Build {

  lazy val bot = Project(
    id = "vindinium-bot",
    base = file("."),
    settings = Defaults.defaultSettings ++ Seq(
      organization := "org.jousse",
      version := "0.1",
      scalaVersion := "2.11.8",
      resolvers += "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play-json" % "2.5.4",
        "org.scalaj" %% "scalaj-http" % "2.3.0",
        "org.scalatest" %% "scalatest" % "3.0.0-M15"
      ),
      scalacOptions ++= Seq("-language:_", "-deprecation", "-unchecked"))
  ).settings(com.github.retronym.SbtOneJar.oneJarSettings: _*)
}
