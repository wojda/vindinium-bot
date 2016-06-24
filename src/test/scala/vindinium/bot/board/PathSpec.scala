package vindinium.bot.board

import testutil.UnitSpec
import vindinium.bot.Move._
import vindinium.bot.Pos

class PathSpec extends UnitSpec {
  val path = Path(Pos(0,0), List(South, East), Pos(1, 1))

  "Path" should "calculate all positions from start to end" in {
    path.positions shouldBe Stream(Pos(0, 0), Pos(1, 0), Pos(1, 1))
  }

  it should "return a new path build from all moves except the last one" in {
    path.init shouldBe Path(path.start, List(South), Pos(1, 0))
  }

  "Path without moves" should "return the same path when asked for init of path" in {
    Path.empty(Pos(5, 5)).init shouldBe Path(Pos(5, 5), List(), Pos(5, 5))
  }
}
