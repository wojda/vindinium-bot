package vindinium

import org.scalacheck.Gen
import vindinium.bot.Pos

trait Generators {

  def positionGen(boardSize: Int) = for {
    x <- Gen.choose(0, boardSize)
    y <- Gen.choose(0, boardSize)
  } yield Pos(x, y)
}
