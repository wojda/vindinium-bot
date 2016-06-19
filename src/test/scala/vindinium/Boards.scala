package vindinium

import play.api.libs.json.Json
import vindinium.bot.Board
import vindinium.client.VindiniumProtocol

trait Boards extends VindiniumProtocol {
  val boardJson = """
                    |{
                    |   "size":10,
                    |   "tiles":"##      ####      ##      ########@4            ####            []        []    $-    ##    ##    $-$-    ##    ##    $-    []      @3[]            ####              ########      ##@1  @2####      ##"
                    | }
                    |""".stripMargin
  val board = Json.parse(boardJson).as[Board]
}
