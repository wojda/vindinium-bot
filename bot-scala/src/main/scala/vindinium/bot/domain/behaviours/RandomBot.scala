package vindinium.bot.domain.behaviours

import vindinium.bot.infrastructure.Input
import vindinium.bot.domain.board.Move._
import vindinium.bot.domain.board.Tile.Wall

import scala.util.Random

class RandomBot extends Bot {

  def move(input: Input) = {
    val move = Random.shuffle(List(North, South, East, West))
      .find(dir ⇒ input.game.board.at(input.hero.pos.to(dir)).exists(Wall !=))
      .getOrElse(Stay)
    (move, this)
  }
}
