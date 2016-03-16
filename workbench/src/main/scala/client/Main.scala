package client

import comms.Client
import org.scalajs.dom.{KeyboardEvent, UIEvent}
import shared.autowire.SharedApi
import shared.models.Shuttle
import autowire._

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.raw.Event

@JSExport
object Main {

  @JSExport
  def main(): Unit = {

    val in = input(placeholder := "Ping String Here").render
    val output = span().render
    val btnGo = input(`type` := "submit", value := "Go").render
    val btnClear = input(`type` := "button", value := "Clear").render

    btnClear.onclick = (e: UIEvent) => {
      in.value = ""
      output.innerHTML = ""
    }

    import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
    btnGo.onclick = (e: Event) => {
      Client[SharedApi].getShuttle(in.value).call().foreach { shuttle: Shuttle =>
        output.appendChild(span(shuttle.ping).render)
        output.appendChild(p().render)
        output.appendChild(span(shuttle.pong).render)
        output.appendChild(p().render)
      }
    }


    in.onkeyup = (e: KeyboardEvent) => {
      e.keyCode match {
        case KeyCode.Enter => btnGo.click()
        case KeyCode.Escape => btnClear.click()
        case _ => None
      }
    }

    dom.document.body.innerHTML = ""
    dom.document.body.appendChild(div(
      in,
      btnGo,
      btnClear,
      p(),
      output
    ).render)
    in.focus()
  }

}