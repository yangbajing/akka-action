
lazy val root = Project(id = "akka-http-starter", base = file("."))
  .settings(
    organization := "me.yangbajing",
    organizationName := "yangbajing-garden",
    organizationHomepage := Some(url("https://yangbajing.me")),
    homepage := Some(url("http://yangbajing.me")),
    startYear := Some(2017),
    scalaVersion := "2.12.1",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-unchecked",
      "-Xlint",
      // "-Yno-adapted-args", //akka-http heavily depends on adapted args and => Unit implicits break otherwise
      "-Ywarn-dead-code"
      // "-Xfuture" // breaks => Unit implicits
    ),
    javacOptions ++= Seq(
      "-encoding", "UTF-8"
    ),
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " },
    assemblyMergeStrategy in assembly := {
      case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
      case "application.conf" => MergeStrategy.concat
      case "META-INF/io.netty.versions.properties" => MergeStrategy.first
      case PathList("org", "slf4j", xs@_*) => MergeStrategy.first
      case "META-INF/native/libnetty-transport-native-epoll.so" => MergeStrategy.first
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    },
    test in assembly := {},
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.4.17",
      "com.typesafe.akka" %% "akka-http" % "10.0.5",
      "com.softwaremill.akka-http-session" %% "core" % "0.4.0",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      "ch.qos.logback" % "logback-classic" % "1.2.1",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.7",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.8.7",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.8.7",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )

