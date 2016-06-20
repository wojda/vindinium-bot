package vindinium.bot.behaviours

import vindinium.bot.{Bot, Input}
import vindinium.bot.Move._
import vindinium.bot.board.interpret.ImplicitBoardReader._

case class LikesGoldButWantToLive() extends Bot {
  override def move(input: Input): (Move, Bot) =
    if(input.hero.life < 30) youAreHurtGoToTavern(input)
    else goToMine(input)

  def youAreHurtGoToTavern(input: Input) = {
    val pathToTavern = input.game.board.nearestTavernFrom(input.hero.pos)
    (pathToTavern.head, LikesGoldButWantToLive())
  }

  def goToMine(input: Input) = {
    input.game.board.nearestMineFrom(input.hero.pos)
      .map(pathToMine => (pathToMine.head, LikesGoldButWantToLive()))
      .getOrElse((Stay, GoldFeverBot()))
  }
}
