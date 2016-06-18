package vindinium.bot

object ImplicitCompass {
  sealed trait Direction
  object North extends Direction
  object NorthEast extends Direction
  object East extends Direction
  object SouthEast extends Direction
  object South extends Direction
  object SouthWest extends Direction
  object West extends Direction
  object NorthWest extends Direction

  implicit class HeroCompass(hero: Hero) {
    def directionTo(destinationPosition: Pos): Direction = {
      (hero.pos.x.compare(destinationPosition.x), hero.pos.y.compare(destinationPosition.y)) match {
        case (1, 1) => NorthEast
        case (1, 0) => East
        case (1, -1) => SouthEast
        case (-1, 1) => NorthWest
        case (-1, 0) => West
        case (-1, -1) => SouthWest
        case (0, -1) => South
        case (0, 1) => North
        case (0, 0) => throw new RuntimeException("Two objects cannot be on the same position")
      }
    }
  }

}
