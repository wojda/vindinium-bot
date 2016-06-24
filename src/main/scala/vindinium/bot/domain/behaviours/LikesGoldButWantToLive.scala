package vindinium.bot.domain.behaviours

import vindinium.bot.infrastructure.Input
import vindinium.bot.domain.board.Move._
import vindinium.bot.domain.board.interpret.ImplicitBoardReader._

case class LikesGoldButWantToLive() extends Bot {
  val minimumHealthPoints = 40

  override def move(input: Input): (Move, Bot) = {
    println("HP: "+input.hero.life)
    if (input.hero.life < minimumHealthPoints) youAreHurtGoToTavern(input)
    else goToMine(input)
  }

  def youAreHurtGoToTavern(input: Input) = {
    input.game.board.pathToNearestTavernFrom(input.hero.pos)
      .map(pathToTavern => (pathToTavern.moves.head, LikesGoldButWantToLive()))
      .getOrElse((Stay, LikesGoldButWantToLive()))
  }

  def goToMine(input: Input) = {
    input.game.board.nearestMineFrom(input.hero)
      .map(pathToMine => (pathToMine.moves.head, LikesGoldButWantToLive()))
      .getOrElse((Stay, LikesGoldButWantToLive()))
  }
}
