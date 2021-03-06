package vindinium.bot.domain

import vindinium.bot.domain.board.{Board, Pos}

case class Hero(
  id: Int,
  name: String,
  pos: Pos,
  life: Int,
  gold: Int,
  mineCount: Int,
  spawnPos: Pos,
  crashed: Boolean,
  elo: Option[Int]) {
    lazy val hasMine: Boolean = mineCount > 0
    override def toString = s"Hero $id $pos life:$life mine:$mineCount gold:$gold"
  }

case class Game(
  id: String,
  turn: Int,
  maxTurns: Int,
  heroes: List[Hero],
  board: Board,
  finished: Boolean) {

  val ranking: List[(Int, Hero)] =
    Stream.from(1)
      .zip(heroes.sortWith(_.gold > _.gold))
      .toList
}
