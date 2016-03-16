package shared.autowire

import autowire._
import upickle._
import shared.models.Shuttle

trait SharedApi {

  def getShuttle(ping: String): Shuttle

}

