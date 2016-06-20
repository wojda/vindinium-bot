package vindinium

import org.scalatest.prop.PropertyChecks
import testutil.UnitTest
import vindinium.bot.Move._
import vindinium.bot.Pos
import vindinium.bot.board.interpret.ImplicitBoardReader._

class BoardReaderSpec extends UnitTest with Boards with PropertyChecks with Generators {

  "BoardReader" should "provide path from position to position bypassing all obstacles" in {
    //given
    val startPos = Pos(2, 3)
    val endPos = Pos(2, 6)
    //when
    val maybePath = board.path(startPos, endPos)
    //then
    maybePath shouldBe Some((endPos, List(South, East, East, East, North)))
  }
}
