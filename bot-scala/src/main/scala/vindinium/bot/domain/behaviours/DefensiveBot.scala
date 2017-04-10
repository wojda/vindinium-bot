package vindinium.bot.domain.behaviours
import vindinium.bot.domain.board.Move._
import vindinium.bot.domain.board.interpret.ImplicitBoardReader._
import vindinium.bot.infrastructure.Input


case class DefensiveBot(previousBehaviour: Bot) extends Bot {
  override def move(input: Input): (Move, Bot) = {
    val nextMove = pathToNearestTavern(input).head
    val nextBehaviour = if(input.percentageOfOwnedMines >= 40) this else previousBehaviour

    (nextMove, nextBehaviour)
  }

  private def pathToNearestTavern(input: Input): List[Move] =
    input.game.board.pathToNearestTavernFrom(input.hero.pos)
      .map(_.moves)
      .getOrElse(List(Stay))

}
