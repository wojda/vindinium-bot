package vindinium

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import testutil.UnitTest
import vindinium.bot.ImplicitBoardReader._
import vindinium.bot.Pos

class BoardReaderSpec extends UnitTest with Boards with PropertyChecks with Generators {

  "BoardReader" should "read board by one step in every direction" in {
    //given
    val center = Pos(board.size/2, board.size/2)
    val pathsMoves = board.allPathsFrom(center).take(12).map{case (_, moves) => moves}.toList
    //expect
    pathsMoves.map(_.size) shouldNot contain(3)
  }

  it should "provide one path per position, no duplicates" in {
    val pathsRange = Gen.choose(min = 1, max = 40)

    forAll(positionGen(board.size), pathsRange) { (startPos: Pos, noPaths) =>
      //when
      val paths: List[Pos] = board.allPathsFrom(startPos).take(noPaths).map{ case (pos, path) => pos}.toList
      //then
      paths shouldNot containDuplicates
    }
  }

  it should "provide paths without duplicates for position = (3, 2)" in {
    //given
    val startPos = Pos(3, 2)
    val noPaths = 6
    //when
    val pathsPositions: List[Pos] = board.allPathsFrom(startPos).take(noPaths).map{ case (pos, path) => pos}.toList
    //then
    pathsPositions shouldNot containDuplicates
  }
}
