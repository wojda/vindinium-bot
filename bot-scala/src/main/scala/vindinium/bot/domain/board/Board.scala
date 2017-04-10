package vindinium.bot.domain.board

case class Board(size: Int, tiles: Vector[Tile]) {

  def at(pos: Pos): Option[Tile] =
    if (pos.isIn(size)) tiles.lift(pos.x * size + pos.y)
    else None
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
