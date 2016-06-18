package vindinium.client

import play.api.libs.json._
import vindinium.bot.{Input, Move}

import scalaj.http.{Http, HttpOptions}

final class VindiniumClient(endpoint: String, key: String) extends Protocol {

  def arena: Input = send {
    Http.post(s"$endpoint/arena").params("key" -> key)
  }

  def training(turns: Int, map: Option[String] = None): Input = send {
    Http.post(s"$endpoint/training").params(
      "key" -> key,
      "turns" -> turns.toString,
      "map" -> map.getOrElse(""))
  }

  def move(url: String, move: Move.Value): Input = send {
    Http.post(url).params(
      "dir" -> move.toString)
  }

  private val timeout = 24 * 60 * 60 * 1000

  def send(req: Http.Request): Input =
    Json.parse(req
      .option(HttpOptions.connTimeout(5000))
      .option(HttpOptions.readTimeout(timeout))
      .asString).as[Input]
}
