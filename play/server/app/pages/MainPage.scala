package pages

import scalatags.Text.all._
import scalatags.Text.tags2.title
import playscalajs.html.scripts


object MainPage {
  val skeleton =
    html(
      head(
        title("Client Play Page"),
        link(rel:="shortcut icon",`type`:="image/png",href:=controllers.routes.Assets.versioned("images/favicon.png").url)
      ),
      body(
        raw(scripts("client").toString())
      )
    )
}