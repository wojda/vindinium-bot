package vindinium.bot.domain.board

object Move extends Enumeration {
  type Move = Value
  val Stay, North, South, East, West = Value
}

