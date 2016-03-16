import sbt.Project.projectToRef

name := "Main Play Project"
version := "1.0-SNAPSHOT"

updateOptions := updateOptions.value.withCachedResolution(true)

lazy val scalaV = "2.11.7"
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val clients = Seq(client)

lazy val server = (project in file("server")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := clients,
  pipelineStages := Seq(scalaJSProd, gzip),
  //resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  libraryDependencies ++= Seq(
    filters,
    "com.vmunier" %% "play-scalajs-scripts" % "0.4.0",
    "org.webjars" % "jquery" % "1.11.1"
  )
).enablePlugins(PlayScala)
  .aggregate(clients.map(projectToRef): _*)
  .dependsOn(sharedJvm)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "0.3.8",
      "com.lihaoyi" %%% "autowire" % "0.2.5",
      "com.lihaoyi" %%% "scalatags" % "0.5.4"
    )
  ).jsConfigure(_ enablePlugins ScalaJSPlay)

lazy val client = (project in file("client")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  mainClass in Compile := Some("client.Main"),
  persistLauncher in Test := false,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.0",
    "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
    // libs for test
    "com.lihaoyi" %%% "utest" % "0.3.1" % "test"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(sharedJs)


onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value