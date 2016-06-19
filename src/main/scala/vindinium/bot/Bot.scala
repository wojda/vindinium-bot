package vindinium.bot

import vindinium.bot.Move._

trait Bot {
  def move(input: Input): (Move, Bot)
}
