package vindinium.bot.board.interpret

import vindinium.bot.Move.Move
import vindinium.bot.Tile.Tavern
import vindinium.bot.{Board, Hero, Pos, Tile}

object ImplicitBoardReader {

  implicit class TileFinder(override val board: Board) extends BoardInterpreter {

    def nearestMineFrom(hero: Hero): Option[List[Move]] = {

      val seekingAMine: PartialFunction[Tile, Boolean] = {
        case Tile.NeutralMine() => true
        case Tile.OwnedMine(heroId) if hero.id != heroId => true
        case _ => false
      }

      findPath(hero.pos, seekingAMine).map(_._2)
    }

    def nearestTavernFrom(pos: Pos): List[Move] =
      findPath(pos, seekingATavern)
        .get //TODO: What if there is no tavern on the board?
        ._2

    private val seekingATavern: PartialFunction[Tile, Boolean] = {
      case Tavern => true
      case _ => false
    }
  }
}
