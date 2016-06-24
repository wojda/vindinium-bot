package vindinium.bot.domain.board.interpret

import vindinium.bot.domain.board.Tile.Air
import vindinium.bot.domain.board.{Board, Path, Pos, Tile}

import scala.annotation.tailrec
import scala.collection.mutable

trait BoardInterpreter {

  val board: Board
  val maxPathLength = 20

  def findPath(start: Pos, seekingTile: Tile => Boolean): Option[Path] = {
    val newExplored = mutable.Set[Pos]()

    def explore(explored: Stream[Path]): Stream[Path] = {
      explored.flatMap(path => path.pathsToAllNeighbours)
        .filter(path => !newExplored.contains(path.end))
        .filter(path => validPos(path.end, seekingTile))
        .map(pathToNeighbour => {
          newExplored += pathToNeighbour.end
          pathToNeighbour
        })
    }

    @tailrec
    def doIt(paths: Stream[Path], seekingTile: Tile => Boolean, counter: Int = 0): Option[Path] =
      if(counter > maxPathLength) { None }
      else {
        val newPaths = explore(paths)
        newPaths.find(path => board.at(path.end).exists(seekingTile)) match {
          case x@Some(path) => Some(Path(path.start, path.moves.reverse, path.end)) //TODO: change logic to remove reversing step
          case None => doIt(newPaths, seekingTile, counter+1)
        }
      }

    doIt(Stream(Path.empty(start)), seekingTile)
  }

  private def validPos(pos: Pos, seekingTile: Tile => Boolean): Boolean =
    board.at(pos).exists {
      case Air => true
      case t => seekingTile(t)
    }
}
