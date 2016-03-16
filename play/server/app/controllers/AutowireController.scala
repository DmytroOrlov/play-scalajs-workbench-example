package controllers

import autowire.AutoWireServer
import shared.autowire.SharedApi

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.mvc._
import shared.models.Shuttle
import upickle.Js

import scala.collection.JavaConversions._
import upickle.default._

class AutowireController extends Controller with SharedApi {

  def getShuttle(pingMessage: String) = {
    Shuttle("ping : \""+pingMessage+"\"","pong: \""+pingMessage.reverse+"\"")
  }

  def autowireAjax(path: String) = Action.async[String](parse.text) { request =>
    val e: String = request.body
    val f: Future[String] = AutoWireServer.route[SharedApi](this)(autowire.Core.Request(
      path.split('/'), upickle.json.read(e).asInstanceOf[Js.Obj].value.toMap)
    ).map(upickle.json.write(_,2))
    val fr: Future[Result] = f.map { s =>
      Ok(s)
    }
    fr
  }
}
