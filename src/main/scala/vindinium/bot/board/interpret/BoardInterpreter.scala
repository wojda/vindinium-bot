package vindinium.bot.board.interpret

import vindinium.bot.Move._
import vindinium.bot.Tile.{Air, Mine}
import vindinium.bot.{Board, Pos, Tile}

import scala.annotation.tailrec
import scala.collection.mutable

trait BoardInterpreter {
  val board: Board
  type Path = (Pos, List[Move])

  def path(start: Pos, end: Pos, seekingTile: Tile = Air): Option[Path] =
    findPath(start, seekingTile, (path) => path._1 == end)

  def findPath(start: Pos, seekingTile: Tile, p: Path => Boolean = (_) => true): Option[Path] = {
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
    def doIt(paths: Stream[(Pos, List[Move])], predicate: Path => Boolean, counter: Int = 0): Option[Path] =
      if(counter > 20) { None }
      else {
        val newPaths = explore(paths)
        newPaths.find(predicate) match {
          case x@Some(path) => Some((path._1, path._2.reverse))
          case None => doIt(newPaths, predicate, counter+1)
        }
      }

    doIt(Stream((start, List[Move]())), p)
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
