package vindinium

import org.scalacheck.Gen
import vindinium.bot.domain.board.Tile.Air
import vindinium.bot.domain.board.{Board, Pos}

trait Generators {

  def heroPositionGenFor(board: Board) = for {
    x <- Gen.choose(0, board.size)
    y <- Gen.choose(0, board.size)
    if board.at(Pos(x, y)).contains(Air)
  } yield Pos(x, y)
}
