package vindinium

import org.scalatest.prop.PropertyChecks
import testutil.UnitTest
import vindinium.bot.ImplicitBoardReader._
import vindinium.bot.Move._
import vindinium.bot.Pos
import vindinium.bot.Tile.Tavern

class FindTavernSpec extends UnitTest with PropertyChecks with Boards with Generators {

  "Board" should "provide path to tavern" in {
    forAll (positionGen(board.size)) { (startPos: Pos) =>
      //when
      val path: List[Move] = board.nearestTavernFrom(startPos)

      //then
      val endPosition = path.foldLeft(startPos)((pos, move) => pos.to(move))
      board.at(endPosition) shouldBe Some(Tavern)
    }
  }

  it should "provide optimal path to tavern" in {
    val startPos = Pos(0, 1)
    board.nearestTavernFrom(startPos) should contain theSameElementsAs List(South, South, South, East)
    board.nearestTavernFrom(startPos.to(East)) should contain theSameElementsAs  List(South, South, South)
    board.nearestTavernFrom(startPos.to(East).to(South)) should contain theSameElementsAs List(South, South)
    board.nearestTavernFrom(startPos.to(South).to(South)) should contain theSameElementsAs List(East, South)
  }

  it should "provide path to the nearest tavern" in {
    pending
  }

}