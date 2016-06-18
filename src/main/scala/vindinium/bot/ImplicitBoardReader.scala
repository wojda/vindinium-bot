package vindinium.bot

object ImplicitBoardReader {

  implicit class TavernFinder(input: Input) {
    def nearestTavern(): Pos = Pos(10,10)
  }
}
