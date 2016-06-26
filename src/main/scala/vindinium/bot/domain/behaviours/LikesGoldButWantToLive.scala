package vindinium.bot.domain.behaviours

import vindinium.bot.infrastructure.Input
import vindinium.bot.domain.board.Move._
import vindinium.bot.domain.board.interpret.ImplicitBoardReader._

case class LikesGoldButWantToLive() extends Bot {
  val minimumHealthPoints = 40

  override def move(input: Input): (Move, Bot) = {
    val nextMove = if (input.hero.life < minimumHealthPoints) youAreHurtGoToTavern(input).head else goToMine(input).head

    (nextMove, this)
  }

  private def youAreHurtGoToTavern(input: Input): List[Move] =
    input.game.board.pathToNearestTavernFrom(input.hero.pos)
      .map(_.moves)
      .getOrElse(List(Stay))

  private def goToMine(input: Input): List[Move] =
    input.game.board.nearestMineFrom(input.hero.pos, excludeMinesOwnedBy = input.hero.id)
      .map(_.moves)
      .getOrElse(List(Stay))
}
