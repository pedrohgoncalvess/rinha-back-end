ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "rinha-de-backend-2e"
  )

val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.0"
val SlickVersion = "3.5.0-M5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  //"ch.megard" %% "akka-http-cors" % "1.2.0" //cors, this version is incompatible,
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "org.postgresql" % "postgresql" % "42.3.4",
  "org.codehaus.jackson" % "jackson-core-lgpl" % "1.9.13",
  "org.json4s" %% "json4s-native" % "4.0.7",

)