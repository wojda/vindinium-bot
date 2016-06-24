package vindinium

import org.scalatest.prop.{Checkers, PropertyChecks}
import testutil.UnitSpec
import vindinium.bot.Move._
import vindinium.bot.Pos
import vindinium.bot.Tile.Tavern
import vindinium.bot.board.interpret.ImplicitBoardReader._
import testutil.PathMatchers._

class FindTavernSpec extends UnitSpec with PropertyChecks with Boards with Generators with Checkers {
  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 300)
  val positionGen = heroPositionGenFor(board)

  "Board" should "provide path to tavern" in {
    forAll(positionGen) { startPos =>
      //when
      val path: List[Move] = board.pathToNearestTavernFrom(startPos).get.moves
      //then
      val endPosition = path.foldLeft(startPos)((pos, move) => pos.to(move))
      board.at(endPosition) shouldBe Some(Tavern)
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
      board.pathToNearestTavernFrom(startPos).get.moves should contain theSameElementsAs expectedMoves
    }
  }

  it should "provide path to the nearest tavern" in {
    pending
  }

  it should "provide path to the nearest tavern bypassing all obstacles" in {
    forAll(positionGen) { heroPosition =>
      val path = board.pathToNearestTavernFrom(heroPosition).get
      path should endAt(Tavern)(board)
      path.init shouldNot containObstacles(board)
    }
  }

  it should "provide path to the nearest tavern from position (1,1) bypassing obstacles" in {
    val path = board.pathToNearestTavernFrom(Pos(1,1)).get
    path should endAt(Tavern)(board)
    path.init shouldNot containObstacles(board)
  }

  it should "provide path where the start position is a given one" in {
    forAll(positionGen) { startPos =>
      board.pathToNearestTavernFrom(startPos).get.start shouldBe startPos
    }
  }
}
