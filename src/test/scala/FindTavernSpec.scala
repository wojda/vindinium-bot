import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import vindinium.bot.Move._
import vindinium.bot.Pos
import vindinium.bot.Tile.Tavern
import Boards._
import vindinium.bot.ImplicitBoardReader._

class FindTavernSpec extends FlatSpec with Matchers with PropertyChecks {

  "Board" should "provide path to tavern" in {
    //given
    val positionGen = for {
      x <- Gen.choose(0, board.size)
      y <- Gen.choose(0, board.size)
    } yield Pos(x, y)

    forAll (positionGen) { (startPos: Pos) =>
      //when
      val path: List[Move] = board.nearestTavernFrom(startPos)

      //then
      val endPosition = path.foldLeft(startPos)((pos, move) => pos.to(move))
      board.at(endPosition) shouldBe Some(Tavern)
    }
  }

  it should "provide optimal path to tavern" in {
    pending
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