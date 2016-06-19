package vindinium.bot.behaviours

import vindinium.bot.ImplicitBoardReader._
import vindinium.bot.Move._
import vindinium.bot.{Bot, Input}

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
      val pathToTavern = input.game.board.nearestTavernFrom(input.hero.pos)
      (pathToTavern.head, TavernFanBot(pathToTavern.tail))
  }
}
