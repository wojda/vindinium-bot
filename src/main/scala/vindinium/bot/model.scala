package vindinium.bot

object Move extends Enumeration {
  type Move = Value
  val Stay, North, South, East, West = Value
}

import vindinium.bot.Move._
import vindinium.bot.board.Path

case class Pos(x: Int, y: Int) {
  private val maxSupportedBoardSize = 50

  def pathsToNeighbours: Set[Path] =
    Set(North, South, West, East)
      .map(m => Path(this, List(m), to(m)))
      .filter(_.end.isIn(maxSupportedBoardSize)) //TODO: it's board logic, move it there

  def neighbors: Set[Pos] = pathsToNeighbours.map(_.end)

  def to(move: Move) = move match {
    case Stay  ⇒ this
    case North ⇒ copy(x = x - 1)
    case South ⇒ copy(x = x + 1)
    case East  ⇒ copy(y = y + 1)
    case West  ⇒ copy(y = y - 1)
  }

  def back(move: Move) = move match {
    case Stay => this
    case North => copy(x = x + 1)
    case South => copy(x = x - 1)
    case East => copy(y = y - 1)
    case West => copy(y = y + 1)
  }

  def isIn(size: Int) = x >= 0 && x < size && y >= 0 && y < size
}

sealed trait Tile
object Tile {
  case object Air extends Tile
  case object Wall extends Tile
  case object Tavern extends Tile
  case class Hero(id: Int) extends Tile
  class Mine extends Tile
  case class NeutralMine() extends Mine
  case class OwnedMine(heroId: Int) extends Mine

  def isObstacle(tile: Tile) = tile match {
    case Air => false
    case _ => true
  }
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
