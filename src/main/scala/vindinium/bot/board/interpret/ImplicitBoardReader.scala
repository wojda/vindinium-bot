package vindinium.bot.board.interpret

import vindinium.bot.Move.Move
import vindinium.bot.Tile.{Mine, Tavern}
import vindinium.bot.{Board, Pos}

object ImplicitBoardReader {

  implicit class TileFinder(override val board: Board) extends BoardInterpreter {

    def nearestMineFrom(pos: Pos): List[Move] =
      findPath(pos, Mine(None), isAMine)
          .get
          ._2

    private def isAMine(path: Path) = board.at(path._1) match {
      case Some(Mine(_)) => true
      case _ => false
    }

    def nearestTavernFrom(pos: Pos): List[Move] =
      findPath(pos, Tavern, isATavern)
        .get //TODO: What if there is no tavern on the board?
        ._2

    private def isATavern(path: Path) = board.at(path._1) match {
      case Some(Tavern) => true
      case _ => false
    }
  }
}
