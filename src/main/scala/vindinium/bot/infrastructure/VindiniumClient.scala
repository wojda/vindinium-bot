package vindinium.bot.infrastructure

import play.api.libs.json._
import vindinium.bot.domain.board.Move

import scala.concurrent.duration._
import scala.language.postfixOps
import scalaj.http.{Http, HttpOptions, HttpRequest}

final class VindiniumClient(endpoint: String, key: String) extends VindiniumProtocol {
  val connectionTimeout = 1 minute
  val readTimeout = 10 seconds

  def arena: Input = send {
    Http(s"$endpoint/arena")
      .timeout(connectionTimeout.toMillis.toInt, readTimeout.toMillis.toInt)
      .postForm.params("key" -> key)
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
