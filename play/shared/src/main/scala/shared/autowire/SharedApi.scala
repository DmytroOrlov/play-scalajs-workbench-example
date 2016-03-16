package shared.autowire

import shared.models.Shuttle

trait SharedApi {

  def getShuttle(ping: String): Shuttle

}

