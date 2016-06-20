package vindinium.bot.behaviours

import vindinium.bot.{Bot, Input}
import vindinium.bot.Move._
import vindinium.bot.board.interpret.ImplicitBoardReader._

object LikesGoldButWantToLive {
  def apply() = new LikesGoldButWantToLive(None)
  def apply(path: List[Move]) = new LikesGoldButWantToLive(Some(path))
  def apply(tavernPath: List[Move], hurt: Boolean) = new LikesGoldButWantToLive(Some(tavernPath), hurt)
}

class LikesGoldButWantToLive(path: Option[List[Move]], hurt: Boolean = false) extends Bot {
  override def move(input: Input): (Move, Bot) = path match {
    case Some(m :: ms) =>
      if(hurt) {
        (m, LikesGoldButWantToLive(ms))
      } else if(input.hero.life <= 20) {
        val pathToTavern = input.game.board.nearestTavernFrom(input.hero.pos)
        (pathToTavern.head, LikesGoldButWantToLive(pathToTavern.tail, hurt = true))
      } else {
        val pathToMine = input.game.board.nearestMineFrom(input.hero.pos)
        (pathToMine.head, LikesGoldButWantToLive(pathToMine.tail))
      }
    case _ =>
      val pathToMine = input.game.board.nearestMineFrom(input.hero.pos)
      (pathToMine.head, LikesGoldButWantToLive(pathToMine.tail))
  }

}
