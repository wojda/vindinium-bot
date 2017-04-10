package vindinium.bot.domain.board

import play.api.libs.json.Json
import vindinium.bot.infrastructure.VindiniumProtocol

import scala.io.Source

trait Boards extends VindiniumProtocol {

  private val boardSize = 10
  private val tailSize = 2
  private val emptyLine = " " * (boardSize*tailSize)
  private def completeWithSpaces(line: String): String = (line+emptyLine).take(boardSize*tailSize)

  private val rawBoard = Source.fromURL(getClass.getResource("/board"))
    .getLines
    .map(completeWithSpaces)
    .mkString

  private val boardJson = s"""
    |{
    |   "size": $boardSize,
    |   "tiles":"$rawBoard"
    |}
    |""".stripMargin

  val board = Json.parse(boardJson).as[Board]
}
