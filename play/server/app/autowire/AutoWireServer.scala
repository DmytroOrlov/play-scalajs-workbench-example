package autowire

import upickle.Js
import upickle.default._

object AutoWireServer extends autowire.Server[Js.Value, Reader, Writer] {
  override def read[Result](p: Js.Value)(implicit evidence$1: Reader[Result]): Result = {
    upickle.default.readJs[Result](p)
  }

  override def write[Result](r: Result)(implicit evidence$2: Writer[Result]): Js.Value = {
    upickle.default.writeJs(r)
  }
}