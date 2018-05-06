name := "ios-messages-archiver"
organization := "io.simonlam"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala) //SbtWeb)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  ws,
  guice,
  "org.webjars" %% "webjars-play" % "2.6.3",
//  "org.webjars.bower" % "react" % "16.1.0",
  "com.google.api-client" % "google-api-client" % "1.23.0",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.23.0",
  "com.google.apis" % "google-api-services-gmail" % "v1-rev83-1.23.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.simonlam.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.simonlam.binders._"
