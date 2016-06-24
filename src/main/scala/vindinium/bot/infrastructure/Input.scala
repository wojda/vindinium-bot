package vindinium.bot.infrastructure

import vindinium.bot.domain.{Game, Hero}

case class Input(
  game: Game,
  hero: Hero,
  token: String,
  viewUrl: String,
  playUrl: String)
