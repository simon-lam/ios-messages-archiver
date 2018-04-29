name := "ios-messages-archiver"
organization := "io.simonlam"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala) //SbtWeb)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice,
  "org.webjars" %% "webjars-play" % "2.6.3",
//  "org.webjars.bower" % "react" % "16.1.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.simonlam.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.simonlam.binders._"
