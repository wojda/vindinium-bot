package vindinium.client

import play.api.libs.json._
import vindinium.bot.{Input, Move}

import scalaj.http.{Http, HttpOptions, HttpRequest}

final class VindiniumClient(endpoint: String, key: String) extends VindiniumProtocol {

  def arena: Input = send {
    Http(s"$endpoint/arena").postForm.params("key" -> key)
  }

  def training(turns: Int, map: Option[String] = None): Input = send {
    Http(s"$endpoint/training").postForm.params(
      "key" -> key,
      "turns" -> turns.toString,
      "map" -> map.getOrElse(""))
  }

  def move(url: String, move: Move.Value): Input = send {
    Http(url).postForm.params(
      "dir" -> move.toString)
  }

  def send(req: HttpRequest): Input =
    Json.parse(req
      .option(HttpOptions.connTimeout(1000))
      .asString.body).as[Input]
}
