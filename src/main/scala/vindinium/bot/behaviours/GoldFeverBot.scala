package vindinium.bot.behaviours

import vindinium.bot.{Bot, Input}
import vindinium.bot.Move._
import vindinium.bot.board.interpret.ImplicitBoardReader._


object GoldFeverBot {
  def apply() = new GoldFeverBot(None)
  def apply(path: List[Move]) = new GoldFeverBot(Some(path))
}

class GoldFeverBot(path: Option[List[Move]]) extends Bot {
  override def move(input: Input): (Move, Bot) = path match {
    case Some(m :: ms) => (m, GoldFeverBot(ms))
    case _ =>
      input.game.board.nearestMineFrom(input.hero.pos)
        .map(pathToMine => (pathToMine.head, GoldFeverBot(pathToMine.tail)))
        .getOrElse((Stay, GoldFeverBot()))
  }

}
