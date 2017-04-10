package vindinium.bot.domain.board

import org.scalacheck.Gen
import vindinium.bot.domain.board.Tile.Air

trait Generators {

  def heroPositionGenFor(board: Board) = for {
    x <- Gen.choose(0, board.size)
    y <- Gen.choose(0, board.size)
    if board.at(Pos(x, y)).contains(Air)
  } yield Pos(x, y)
}
