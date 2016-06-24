package vindinium.bot.domain.board.interpret

import vindinium.bot.domain.Hero
import vindinium.bot.domain.board.Tile.Tavern
import vindinium.bot.domain.board.{Board, Path, Pos, Tile}

object ImplicitBoardReader {

  implicit class TileFinder(override val board: Board) extends BoardInterpreter {

    def nearestMineFrom(hero: Hero): Option[Path] = {
      val seekingAMine: PartialFunction[Tile, Boolean] = {
        case Tile.NeutralMine() => true
        case Tile.OwnedMine(heroId) if hero.id != heroId => true
        case _ => false
      }

      findPath(hero.pos, seekingAMine)
    }

    def pathToNearestTavernFrom(pos: Pos): Option[Path] = findPath(pos, seekingATavern)

    private val seekingATavern: PartialFunction[Tile, Boolean] = {
      case Tavern => true
      case _ => false
    }
  }
}
