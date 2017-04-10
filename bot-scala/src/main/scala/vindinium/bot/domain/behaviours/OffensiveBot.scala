package vindinium.bot.domain.behaviours
import vindinium.bot.domain.board.Move
import vindinium.bot.domain.board.Move.Move
import vindinium.bot.infrastructure.Input
import vindinium.bot.domain.board.interpret.ImplicitBoardReader._

case class OffensiveBot() extends Bot {
  override def move(input: Input): (Move, Bot) = (pathToTheNearestHero(input).head, this)

  private def pathToTheNearestHero(input: Input): List[Move] =
    input.game.board
      .pathToNearestHeroFrom(input.hero.pos, excludedHeroId = input.hero.id)
      .map(_.moves)
      .getOrElse(List(Move.Stay))
}
