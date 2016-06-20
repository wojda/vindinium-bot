package vindinium.bot.board.interpret

import vindinium.bot.Move._
import vindinium.bot.Tile.Air
import vindinium.bot.{Board, Pos, Tile}

import scala.collection.mutable

trait BoardInterpreter {
  val board: Board
  type Path = (Pos, List[Move])

  def path(start: Pos, end: Pos, seekingTile: Tile = Air): Option[Path] =
    findPath(start, seekingTile, (path) => path._1 == end)

  def findPath(start: Pos, seekingTile: Tile, p: Path => Boolean = (_) => true): Option[Path] = {
    val newExplored = mutable.Set[Pos]()

    def expand(explored: Stream[Path]): Stream[Path] = {
      explored.flatMap(path => pathsToAllNeighbours(path))
        .filter(path => !newExplored.contains(path._1))
        .filter(path => validPos(path._1, seekingTile))
        .map(pathToNeighbour => {
          newExplored += pathToNeighbour._1
          pathToNeighbour
        })
    }

    var paths = Stream((start, List[Move]()))
    1 to 20 foreach { _ =>
      paths = expand(paths)
      paths
        .find(p) match {
        case x@Some(path) => return Some((path._1, path._2.reverse))
        case None => ()
      }
    }

    None
  }

  private def pathsToAllNeighbours(path: Path): Stream[Path] =
    path._1
      .neighborsWithMove
      .map(n => (n._1, n._2 :: path._2)).toStream

  private def validPos(pos: Pos, seekingTile: Tile): Boolean = board.at(pos) match {
    case Some(Air) => true
    case Some(`seekingTile`) => true
    case _ => false
  }

}
