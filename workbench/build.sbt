enablePlugins(ScalaJSPlugin)

workbenchSettings

name := "Client Dev"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.lihaoyi" %%% "scalatags" % "0.5.4",
  "com.lihaoyi" %%% "upickle" % "0.3.8",
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "com.lihaoyi" %%% "autowire" % "0.2.5",
  // libs for test
  "com.lihaoyi" %%% "utest" % "0.3.1" % "test"
)

jsDependencies += RuntimeDOM


scalaVersion := "2.11.7"
persistLauncher := true
mainClass in Compile := Some("client.Main")
persistLauncher in Test := false
bootSnippet := "client.Main().main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

testFrameworks += new TestFramework("utest.runner.Framework")
