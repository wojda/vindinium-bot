package vindinium.client

import play.api.libs.functional.syntax._
import play.api.libs.json._
import vindinium.bot._

import scala.util.Try


trait VindiniumProtocol {

  implicit val posReads = Json.reads[Pos]
  implicit val boardReads = (
    (__ \ "size").read[Int] and
    (__ \ "tiles").read[String].map {
        _.grouped(2).toVector map parseTile
      }
    ) (Board.apply _)
  implicit val heroReads = Json.reads[Hero]
  implicit val gameReads = Json.reads[Game]
  implicit val inputReads = Json.reads[Input]

  def parseTile(str: String): Tile = str.toList match {
    case List(' ', ' ') ⇒ Tile.Air
    case List('#', '#') ⇒ Tile.Wall
    case List('[', ']') ⇒ Tile.Tavern
    case List('$', '-') ⇒ Tile.NeutralMine()
    case List('$', x) ⇒ Tile.OwnedMine(int(x).get)
    case List('@', x) ⇒ Tile.Hero(int(x) getOrElse sys.error(s"Can't parse $str"))
    case x ⇒ sys error s"Can't parse $str"
  }

  private def int(c: Char): Option[Int] = Try(java.lang.Integer.parseInt(c.toString)).toOption
}
