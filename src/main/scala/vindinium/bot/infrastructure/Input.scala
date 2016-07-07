package vindinium.bot.infrastructure

import vindinium.bot.domain.board.Tile.{NeutralMine, OwnedMine}
import vindinium.bot.domain.{Game, Hero}

case class Input(
  game: Game,
  hero: Hero,
  token: String,
  viewUrl: String,
  playUrl: String) {

  lazy val percentageOfOwnedMines: Int = {
    val heroId = hero.id
    var ownedByMe = 0
    var other = 0

    game.board.tiles.foreach{
      case OwnedMine(`heroId`) => ownedByMe = ownedByMe + 1
      case OwnedMine(_) => other = other + 1
      case NeutralMine() => other = other + 1
      case _ => ()
    }

    if(ownedByMe == 0) 0 else ((ownedByMe.toDouble / (other + ownedByMe)) * 100).toInt
  }
}
