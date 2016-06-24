package vindinium.bot.domain.board

import vindinium.bot.domain.board.Move._

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
