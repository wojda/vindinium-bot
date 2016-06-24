package testutil

import org.scalatest.matchers.{MatchResult, Matcher}
import vindinium.bot.board.Path
import vindinium.bot.{Board, Tile}

object PathMatchers extends PathMatchers

trait PathMatchers {

  def endAt(tile: Tile)(board: Board): Matcher[Path] = new PathEndMatcher(tile, board)
  def containObstacles(board: Board): Matcher[Path] = new PathObstaclesMatcher(board)

  private class PathEndMatcher(expectedTile: Tile, board: Board) extends Matcher[Path] {
    override def apply(path: Path): MatchResult = {
      val foundTile = board.at(path.end).getOrElse(throw new IllegalArgumentException(s"End position of the path: ${path.end} is outside the board."))
      MatchResult(
        foundTile == expectedTile,
        s"End of path is a $foundTile, expected: $expectedTile",
        s"End of path is a $foundTile, expected not a $expectedTile"
      )
    }
  }

  private class PathObstaclesMatcher(board: Board) extends Matcher[Path] {
    override def apply(path: Path): MatchResult = {
      val maybeObstacle = path.positions.init.find( p => {
        val obstacle = board.at(p).get
        Tile.isObstacle(obstacle)
      } )
      MatchResult(
        maybeObstacle.isDefined,
        "Path should contain obstacles",
        s"The path must not contains obstacles, however obstacles found"
      )
    }
  }

}
