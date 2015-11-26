import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys

object Build extends Build {

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  lazy val root = Project("akka-action", file("."))
    .settings(
      description := "Akka Action",
      version := "0.0.2",
      homepage := Some(new URL("https://github.com/yangbajing/akka-action")),
      organization := "me.yangbajing",
      organizationHomepage := Some(new URL("https://github.com/yangbajing/akka-action")),
      startYear := Some(2015),
      scalaVersion := "2.11.7",
      scalacOptions ++= Seq(
        "-encoding", "utf8",
        "-unchecked",
        "-feature",
        "-deprecation"
      ),
      javacOptions ++= Seq(
        "-encoding", "utf8",
        "-Xlint:unchecked",
        "-Xlint:deprecation"
      ),
      publish :=(),
      publishLocal :=(),
      publishTo := None,
      offline := true,
      fork := true,
      resolvers ++= Seq(
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
        "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
        "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"),
      AssemblyKeys.assemblyJarName in AssemblyKeys.assembly := "email-server.jar",
      mainClass in AssemblyKeys.assembly := Some("me.yangbajing.emailserver.Main"),
      libraryDependencies ++= Seq(
        _json4sJackson,
        _akkaHttp,
        _akkaActor,
        _akkaSlf4j,
        _logback,
        _typesafeConfig,
        _scalaLogging,
        _scalatest))

  val _scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.4"
  val _scalatest = "org.scalatest" %% "scalatest" % "2.2.5" % "test"
  val _typesafeConfig = "com.typesafe" % "config" % "1.3.0"
  val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.1.0").exclude("org.scala-lang", "scala-library").exclude("org.scala-lang", "scala-reflect")

  val verAkkaHttp = "2.0-M1"
  val _akkaStream = "com.typesafe.akka" %% "akka-stream-experimental" % verAkkaHttp
  val _akkaHttpCore = "com.typesafe.akka" %% "akka-http-core-experimental" % verAkkaHttp
  val _akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % verAkkaHttp

  val verAkka = "2.3.14"
  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka

  val _redisclient = "net.debasishg" %% "redisclient" % "3.0"

  val _json4sJackson = "org.json4s" %% "json4s-jackson" % "3.3.0"

  val _mongoScala = "org.mongodb.scala" %% "mongo-scala-driver" % "1.0.0"

  val _cassandra = "com.datastax.cassandra" % "cassandra-driver-core" % "2.2.0-rc3"

  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.3"
  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.4"
  val _guava = "com.google.guava" % "guava" % "18.0"

}

