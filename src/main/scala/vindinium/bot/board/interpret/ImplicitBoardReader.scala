package vindinium.bot.board.interpret

import vindinium.bot.Move.Move
import vindinium.bot.Tile.Tavern
import vindinium.bot.{Board, Pos}

object ImplicitBoardReader {

  implicit class TavernFinder(override val board: Board) extends BoardInterpreter {

    def nearestTavernFrom(pos: Pos): List[Move] = {
      findPath(pos, Tavern, isATavern)
        .get //TODO: What if there is no tavern on the board?
        ._2
    }

    private def isATavern(path: Path) = board.at(path._1) match {
      case Some(Tavern) => true
      case x => false
    }
  }
}
