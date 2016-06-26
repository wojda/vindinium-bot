package vindinium.bot.domain.behaviours
import vindinium.bot.domain.board.Move._
import vindinium.bot.domain.board.Path
import vindinium.bot.infrastructure.Input
import vindinium.bot.domain.board.interpret.ImplicitBoardReader._

case class BigBadWolfBot() extends Bot {
  val minimumHealthPoints = 40

  override def move(input: Input): (Move, Bot) = {
    import input._
    val pathToNearestEnemy = game.board.pathToNearestHeroFrom(hero.pos, excludedHeroId = hero.id)

    val nextMove =
      if(hero.life < minimumHealthPoints) pathToTavern(input).head
      else if(enemyIsCloseAndHasMine(pathToNearestEnemy, input)) pathToNearestEnemy.get.moves.head
      else pathToMine(input).head

    (nextMove, this)
  }

  private def pathToTavern(input: Input): List[Move] =
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
