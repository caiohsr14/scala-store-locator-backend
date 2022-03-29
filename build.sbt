ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "store-locator-backend"
  )

val Http4sVersion  = "0.23.11"
val CirceVersion   = "0.14.1"

libraryDependencies ++= Seq(
  "org.log4s"           %% "log4s" % "1.10.0",
  "org.http4s"          %% "http4s-ember-server" % Http4sVersion,
  "org.http4s"          %% "http4s-ember-client" % Http4sVersion,
  "org.http4s"          %% "http4s-circe"        % Http4sVersion,
  "org.http4s"          %% "http4s-dsl"          % Http4sVersion,
  "io.circe"            %% "circe-generic"       % CirceVersion,
  "io.circe"            %% "circe-parser"        % CirceVersion,
  // Java Libs
  "ch.qos.logback"      % "logback-classic" % "1.2.11",
  "com.typesafe"        % "config" % "1.4.2",
)

Compile / packageBin / mainClass := Some("com.helio.http.Server")
Compile / run / mainClass := Some("com.helio.http.Server")
