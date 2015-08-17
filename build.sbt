name := "scalafx-akka"

version := "1.0"

scalaVersion := "2.11.6"

// Scala FX
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.40-R8"

// Akka
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.12"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.12"

// Test lib
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Prevent startup bug in JavaFX
fork := true
