package vindinium.bot.domain.board.interpret

import vindinium.bot.domain.Hero
import vindinium.bot.domain.board.Tile.Tavern
import vindinium.bot.domain.board.{Board, Path, Pos, Tile}

object ImplicitBoardReader {

  implicit class TileFinder(override val board: Board) extends BoardInterpreter {

    def nearestMineFrom(hero: Hero): Option[Path] = {
      val seekingAMine: PartialFunction[Tile, Boolean] = {
        case Tile.NeutralMine() => true
        case Tile.OwnedMine(mineOwnerId) if hero.id != mineOwnerId => true
        case _ => false
      }

      findPath(hero.pos, seekingAMine)
    }

    def pathToNearestTavernFrom(pos: Pos): Option[Path] = findPath(pos, {
      case Tavern => true
      case _ => false
    })

    def pathToNearestHeroFrom(pos: Pos, excludedHeroId: Int = -1): Option[Path] = findPath(pos, {
      case Tile.Hero(id) if id != excludedHeroId => true
      case _ => false
    })
  }

}
