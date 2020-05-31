import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
import com.typesafe.sbt.SbtNativePackager.autoImport.maintainer
import sbt.Keys.{libraryDependencies, publishArtifact, sources, version}


name := """itog1"""
organization := "com.example"
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

//libraryDependencies += "com.typesafe.slick" %% "slick-extensions" % "3.0.0"
//libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0"
//libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"


libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
  "com.microsoft.sqlserver" % "mssql-jdbc" % "6.2.1.jre8"
)

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

