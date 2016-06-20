package vindinium.bot.board.interpret

import vindinium.bot.Move._
import vindinium.bot.Tile.Air
import vindinium.bot.{Board, Pos, Tile}

import scala.annotation.tailrec
import scala.collection.mutable

trait BoardInterpreter {
  val board: Board
  type Path = (Pos, List[Move])
  val maxPathLength = 20

  def findPath(start: Pos, seekingTile: Tile => Boolean): Option[Path] = {
    val newExplored = mutable.Set[Pos]()

    def explore(explored: Stream[Path]): Stream[Path] = {
      explored.flatMap(path => pathsToAllNeighbours(path))
        .filter(path => !newExplored.contains(path._1))
        .filter(path => validPos(path._1, seekingTile))
        .map(pathToNeighbour => {
          newExplored += pathToNeighbour._1
          pathToNeighbour
        })
    }

    @tailrec
    def doIt(paths: Stream[(Pos, List[Move])], seekingTile: Tile => Boolean, counter: Int = 0): Option[Path] =
      if(counter > maxPathLength) { None }
      else {
        val newPaths = explore(paths)
        newPaths.find(path => board.at(path._1).exists(seekingTile)) match {
          case x@Some(path) => Some((path._1, path._2.reverse))
          case None => doIt(newPaths, seekingTile, counter+1)
        }
      }

    doIt(Stream((start, List[Move]())), seekingTile)
  }

  private def pathsToAllNeighbours(path: Path): Stream[Path] =
    path._1
      .neighborsWithMove
      .map(n => (n._1, n._2 :: path._2)).toStream

  private def validPos(pos: Pos, seekingTile: Tile => Boolean): Boolean =
    board.at(pos).exists {
      case Air => true
      case t => seekingTile(t)
    }
}
