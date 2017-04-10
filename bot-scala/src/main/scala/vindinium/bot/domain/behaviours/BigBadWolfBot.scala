package vindinium.bot.domain.behaviours
import vindinium.bot.domain.board.Move._
import vindinium.bot.domain.board.Path
import vindinium.bot.domain.board.interpret.ImplicitBoardReader._
import vindinium.bot.infrastructure.Input

case class BigBadWolfBot() extends Bot {
  val minimumHealthPoints = 25

  override def move(input: Input): (Move, Bot) = {
    import input._
    lazy val pathToTavern: List[Move] = pathToNearestTavern(input)
    lazy val pathToNearestEnemy: Option[Path] = game.board.pathToNearestHeroFrom(hero.pos, excludedHeroId = hero.id)

    val nextMove =
      if(pathToTavern.size == 1 && hero.life < 90) pathToTavern.head
      else if(hero.life < minimumHealthPoints) pathToTavern.head
      else if(enemyIsCloseAndHasMine(pathToNearestEnemy, input)) pathToNearestEnemy.get.moves.head
      else pathToMine(input).head

    val became = if(input.percentageOfOwnedMines > 40) DefensiveBot(this) else this

    (nextMove, became)
  }

  private def pathToNearestTavern(input: Input): List[Move] =
    input.game.board.pathToNearestTavernFrom(input.hero.pos)
      .map(_.moves)
      .getOrElse(List(Stay))

  private def enemyIsCloseAndHasMine(pathToEnemy: Option[Path], input: Input): Boolean =
    pathToEnemy
      .filter(path => path.moves.size <= 5)
      .flatMap(path => input.game.heroes.find(_.pos == path.end))
      .exists(hero => hero.hasMine)

  private def pathToMine(input: Input): List[Move] =
    input.game.board.nearestMineFrom(input.hero.pos, excludeMinesOwnedBy = input.hero.id)
      .map(_.moves)
      .getOrElse(List(Stay))
}
