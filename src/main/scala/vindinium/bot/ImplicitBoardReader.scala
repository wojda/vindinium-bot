package vindinium.bot

import vindinium.bot.Move.Move
import vindinium.bot.Tile.Tavern

object ImplicitBoardReader {
  type Path = (Pos, List[Move])

  implicit class TavernFinder(board: Board) {

    private def neighborsWithHistory(path: Path): Stream[Path] =
      path._1.neighborsWithMove.map(n => (n._1, n._2 :: path._2)).toStream

    private def filterOutExploredPositions(paths: Stream[Path], explored: Set[Pos]): Stream[Path] =
      paths.filter(path => !explored.contains(path._1))

    private def from(initial: Stream[Path], explored: Set[Pos]): Stream[Path] = {
      val positionsToExplore = filterOutExploredPositions( neighborsWithHistory(initial.head), explored)
      val newExplored = positionsToExplore.map(_._1).toSet ++ explored
      initial #::: positionsToExplore #::: positionsToExplore.flatMap(p => from(Stream(p), newExplored))
    }

    def allPathsFrom(pos: Pos): Stream[Path] = from(Stream((pos, List())), explored = Set())

    def nearestTavernFrom(pos: Pos): List[Move] =
      allPathsFrom(pos)
        .find(pos => board.at(pos._1) match {
          case Some(Tavern) => true
          case x => false })
        .get //TODO What if there is no tavern on the board?
        ._2
  }

}
