package vindinium.bot.domain.behaviours

import vindinium.bot.infrastructure.Input
import vindinium.bot.domain.board.Move._
import vindinium.bot.domain.board.interpret.ImplicitBoardReader._

/**
  * The goal of the life of this bot is to find a tavern and stay there as long as possible.
  * The bot do not take care about gold, war, fame or rankings. It just wants to find his tavern.
  */
object TavernFanBot {
  def apply() = new TavernFanBot(None)
  def apply(path: List[Move]) = new TavernFanBot(Some(path))
}

class TavernFanBot(path: Option[List[Move]]) extends Bot {

  override def move(input: Input): (Move, Bot) = path match {
    case Some(m :: ms) => (m, TavernFanBot(ms))
    case Some(Nil) => (Stay, TavernFanBot(List()))
    case None =>
      input.game.board.pathToNearestTavernFrom(input.hero.pos)
        .map(pathToTavern => (pathToTavern.moves.head, LikesGoldButWantToLive()))
        .getOrElse((Stay, LikesGoldButWantToLive()))
  }
}
