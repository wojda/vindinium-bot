package vindinium.bot

import vindinium.bot.Move.Move
import vindinium.bot.Tile.Tavern

import scala.collection.mutable

object ImplicitBoardReader {
  type Path = (Pos, List[Move])

  implicit class TavernFinder(board: Board) {

    private def pathsToAllNeighbours(path: Path): Stream[Path] =
      path._1.neighborsWithMove.map(n => (n._1, n._2 :: path._2)).toStream

    private def explore(initial: Stream[Path], explored: Set[Pos]): Stream[Path] = {
      val newExplored = mutable.Set(explored.toSeq: _*)

      val notExploredNeighbours = initial.flatMap(path => pathsToAllNeighbours(path)
          .filter(path => !newExplored.contains(path._1))
          .map(pathToNeighbour => {
            newExplored += pathToNeighbour._1
            pathToNeighbour
          })
      )

      notExploredNeighbours #::: explore(notExploredNeighbours, newExplored.toSet)
    }

    def allPathsFrom(pos: Pos): Stream[Path] = explore(Stream((pos, List())), explored = Set())

    def nearestTavernFrom(pos: Pos): List[Move] =
      allPathsFrom(pos)
        .find(path => board.at(path._1) match {
          case Some(Tavern) => true
          case x => false })
        .get //TODO: What if there is no tavern on the board?
        ._2
  }

}
