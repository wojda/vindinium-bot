package vindinium.bot

import vindinium.bot.Move._
import vindinium.bot.Tile.Wall

import scala.util.Random

trait Bot {
  def move(input: Input): Move
}

class RandomBot extends Bot {

  def move(input: Input) =
    Random.shuffle(List(North, South, East, West))
      .find(dir ⇒ input.game.board.at(input.hero.pos.to(dir)).exists(Wall !=))
      .getOrElse(Stay)
}

class TavernFan extends Bot {
  import ImplicitBoardReader._
  import ImplicitCompass._
  override def move(input: Input): Move = {
    import input._
    val tavernPosition = input.nearestTavern()
    hero.directionTo(tavernPosition) match {
      case North | NorthEast | NorthWest => Move.North
      case South | SouthEast | SouthWest => Move.South
      case East => Move.East
      case West => Move.West
    }
  }
}