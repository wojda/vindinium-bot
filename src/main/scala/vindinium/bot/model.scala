package vindinium.bot

object Move extends Enumeration {
  type Move = Value
  val Stay, North, South, East, West = Value
}

import vindinium.bot.Move._

case class Pos(x: Int, y: Int) {

  def neighborsWithMove: Set[(Pos, Move)] =
    Set(North, South, West, East)
      .map(m => (to(m), m))
      .filter(n => n._1.x >= 0 && n._1.y >= 0)
      .filter(n => n._1.x <= 10 && n._1.y <= 10) //TODO: it's board logic, move it

  def neighbors: Set[Pos] = neighborsWithMove.map(_._1)

  def to(move: Move) = move match {
    case Stay  ⇒ this
    case North ⇒ copy(x = x - 1)
    case South ⇒ copy(x = x + 1)
    case East  ⇒ copy(y = y + 1)
    case West  ⇒ copy(y = y - 1)
  }

  def isIn(size: Int) = x >= 0 && x < size && y >= 0 && y < size
}

sealed trait Tile
object Tile {
  case object Air extends Tile
  case object Wall extends Tile
  case object Tavern extends Tile
  case class Hero(id: Int) extends Tile
  case class Mine(heroId: Option[Int]) extends Tile
}

case class Board(size: Int, tiles: Vector[Tile]) {

  def at(pos: Pos): Option[Tile] =
    if (pos.isIn(size)) tiles.lift(pos.x * size + pos.y)
    else None
}

case class Hero(
  id: Int,
  name: String,
  pos: Pos,
  life: Int,
  gold: Int,
  mineCount: Int,
  spawnPos: Pos,
  crashed: Boolean,
  elo: Option[Int]) {
    override def toString = s"Hero $id $pos life:$life mine:$mineCount gold:$gold"
  }

case class Game(
  id: String,
  turn: Int,
  maxTurns: Int,
  heroes: List[Hero],
  board: Board,
  finished: Boolean)

case class Input(
  game: Game,
  hero: Hero,
  token: String,
  viewUrl: String,
  playUrl: String)
