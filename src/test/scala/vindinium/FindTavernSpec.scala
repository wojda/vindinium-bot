package vindinium

import org.scalatest.prop.PropertyChecks
import testutil.UnitTest
import vindinium.bot.Move._
import vindinium.bot.Pos
import vindinium.bot.Tile.{Air, Tavern}
import vindinium.bot.board.interpret.ImplicitBoardReader._

class FindTavernSpec extends UnitTest with PropertyChecks with Boards with Generators {

  "Board" should "provide path to tavern" in {
    forAll (positionGen(board.size)) { (startPos: Pos) => whenever(board.at(startPos).contains(Air)) {
        //when
        val path: List[Move] = board.nearestTavernFrom(startPos)
        //then
        val endPosition = path.foldLeft(startPos)((pos, move) => pos.to(move))
        board.at(endPosition) shouldBe Some(Tavern)
      }
    }
  }

  val respawnPos = Pos(0, 1)
  val testCases = Table(
    ("start position", "expected list of moves"),
    (respawnPos, List(South, South, South, East)),
    (respawnPos.to(East), List(South, South, South)),
    (respawnPos.to(East).to(South), List(South, South)),
    (respawnPos.to(South).to(South), List(East, South)))

  forAll(testCases) { (startPos, expectedMoves) =>
    it should s"provide the shortest path to tavern from $startPos" in {
      board.nearestTavernFrom(startPos) should contain theSameElementsAs expectedMoves
    }
  }

  it should "provide path to the nearest tavern" in {
    pending
  }

  it should "provide path to the nearest tavern bypassing all obstacles" in {
    pending
  }
}
