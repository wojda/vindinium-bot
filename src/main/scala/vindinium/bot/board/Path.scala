package vindinium.bot.board

import vindinium.bot.Move._
import vindinium.bot.Pos

object Path {
  def empty(pos: Pos): Path = Path(pos, List(), pos)
}

case class Path(start: Pos, moves: List[Move], end: Pos) {

  lazy val init: Path = Path(start, moves.dropRight(1), end.back(moves.lastOption.getOrElse(Stay)))

  lazy val positions: Stream[Pos] = start #:: positions.zip(moves).map(z => z._1.to(z._2))

  lazy val pathsToAllNeighbours: Stream[Path] =
    end.pathsToNeighbours
      .map(p => Path(start, p.moves ::: moves, p.end)).toStream
}
