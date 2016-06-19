package vindinium.bot.behaviours

import vindinium.bot.Move._
import vindinium.bot.Tile.Wall
import vindinium.bot.{Bot, Input}

import scala.util.Random

class RandomBot extends Bot {

  def move(input: Input) = {
    val move = Random.shuffle(List(North, South, East, West))
      .find(dir ⇒ input.game.board.at(input.hero.pos.to(dir)).exists(Wall !=))
      .getOrElse(Stay)
    (move, this)
  }
}
