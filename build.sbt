ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "rinha-de-backend-2e"
  )

val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.0"
val SlickVersion = "3.5.0-M5"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "org.postgresql" % "postgresql" % "42.3.4",
  "org.codehaus.jackson" % "jackson-core-lgpl" % "1.9.13",
  "org.json4s" %% "json4s-native" % "4.0.7",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.15.3",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.15.3",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.15.3"

)