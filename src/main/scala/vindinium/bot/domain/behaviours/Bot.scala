package vindinium.bot.domain.behaviours

import vindinium.bot.infrastructure.Input
import vindinium.bot.domain.board.Move.Move

trait Bot {
  def move(input: Input): (Move, Bot)
}
