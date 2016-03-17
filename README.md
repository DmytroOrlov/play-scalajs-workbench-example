# Play Scala.js Workbench Example
This repository contains an example setup showing how to use [Scala.js](https://www.scala-js.org/) with [Play](https://www.playframework.com/) while using lihaoyi's [Workbench plugin](https://github.com/lihaoyi/workbench) for client development efficiency.

**Quick Start**

It is assumed that you have `git` and `sbt` set up and on your path. If you use `activator`, just replace the term `sbt` with `activator`.

Open two command-line shells at the same directory (of your choice, e.g. `projects`)

In first command shell:
```
git clone https://github.com/aholland/play-scalajs-workbench-example.git
cd play-scalajs-workbench-example
cd play
sbt run
```
Test at <http://localhost:9000>

Leave that running and in the second shell:
```
cd play-scalajs-workbench-example
cd workbench
sbt ~fastOptJS
```
Test at <http://localhost:12345/target/scala-2.11/classes/index-dev.html>

In Chrome, press F12 to see developer tools and navigate to the Console window which will show all the console output including compile errors.

Import the `sbt` project under `workbench` into your IDE. Expermient and make changes to the client code.


**Explanation**

This git repository contains two sbt projects alongside each other, one in subdirectory `play` and one in subdirectory `workbench`.

In directory `play` is the Main Play Project. Everything you need to run the demo app as a Play project is under this directory. On the command-line just go into the `play` directory and execute `sbt run`. Once Play is running you should be able to test the simple demo web app at <http://localhost:9000> This Play project was created from a standard Play template project created using `activator new <projectname> play-scala`. Using https://github.com/vmunier/play-with-scalajs-example as a reference I built a simple client-server web app with a very simple shared data structure, one case class called Shuttle, which represents a message sent from the server to the client. I also deleted some of the unnecessary code put in by the Play 2.5.0 activator template, i.e. the `HomeController`, `AsynchController` and a few other classes that are instructive but not directly relevant to this example setup. This project (in the `play` directory) is a very similar setup to https://github.com/vmunier/play-with-scalajs-example i.e. is simply divided into a `server` project (compiled to the JVM), a `client` project (compiled to JavaScript) and a `shared` project compiled to both. Ajax communication is handled by [`AutoWire`](https://github.com/lihaoyi/autowire) and `"microPickle"` [`(uPickle)`](https://github.com/lihaoyi/upickle-pprint). This would seem to be a good and productive setup, especially in combination with something like the Play AutoRefresh plugin. But in practice it is typically painfully slow on account of the fact that as you develop the client you probably want to test hundreds of little changes and will frequently cause a compilation error. Each time you get a compile error you will recieve a descriptive error in the browser from Play including stack-trace etc. ... and you will cause Play to reload. This is time-consuming, and if you have database resources etc. it could be very time-consuming. I have found that typically when I am developing my client I am not simultaneously making lots of changes to the server and the shared data-structures and what I actually want is to be able to fiddle with the client-only code and see the results as quickly as possible without affecting my running server at all. This is where the second project comes in:

In the directory `workbench` is another sbt project which is set up exactly like a client-only Scala.js Workbench project as per any of the demo apps at <https://github.com/lihaoyi/workbench-example-app>. What's special though is that the code in the `workbench` project (code under `workbench/src/main/scala`) is just a copy of the *client* code in the `play` project  (from `play/client/src/main/scala`) plus a copy of the *shared* code (from `play/shared/src/main/scala`). i.e. the stand-alone client project in directory `workbench` contains a copy of the all the code the client needs, which is naturally "client-only plus shared". To run this copy of the client in its own process, open a new command-line shell (i.e. *leave the Play server running*), change to the `workbench` directory in the new shell, and execute the command `sbt ~fastOptJS`. Once you have done so you should be able to test the simple demo web app once again, but this time at <http://localhost:12345/target/scala-2.11/classes/index-dev.html>. Remember that while the workbench is serving on port 12345 the Play server must still be running in a separate process serving on port 9000 so that it can respond to Ajax calls made by the web app client.

Because the client is running in its own process with its own server (i.e. lihaoyi's Workbench plugin which acts as a simple server using port 12345) but is making Ajax calls to the separate Play server on port 9000, one final adjustment needed to be made to the Play project: the [CORS filter](https://www.playframework.com/documentation/2.5.x/CorsFilter) needed to be activated to authorise [cross-origin requests](https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS) by adding the appropriate `Access-Control-Request-Headers:` to all Ajax responses. See the `application.conf` file, lines 217 to 227, and the class `Filters` directly under the `app` directory.

The idea is to develop with both the Play server and the Workbench running and the two projects open in separate IDE instances, and whenever you are happy with your changes in the client, to copy them back into the appropriate directories in the `play` project. As a starting suggestion, I would recommend doing so with your favourite file manager or small script before every git commit.

Similarly, if you make changes to the shared code you will need to copy those to the other project before your app will function correctly. 

Clearly this manual solution is crying out for a plugin to obviate having to copy the files around, but in practice at the moment this setup allows me to tinker endlessly with the client without interfering with the server, and still keep all the advantages of a having a Scala code-base continuous across client and server.

***Note:*** in Chrome you can press F12 to see developer tools and then navigate to the Console window which will show all the console output including compile errors and reload messages.
  
Forks, pull-requests and contact welcome. 