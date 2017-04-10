package vindinium.bot.infrastructure

import org.slf4j.LoggerFactory
import os.Scripts._
import vindinium.bot.domain.behaviours.{BigBadWolfBot, Bot}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import Integer.parseInt

object App {
  private val log = LoggerFactory.getLogger("Vindinium")

  def main(args: Array[String]): Unit = makeClient match {
    case Left(error) ⇒
      log.error("Could not create vindimium client. Error: {}", error)
    case Right(server) ⇒ args match {
      case Array() ⇒
        training(server, _.training(100))
      case Array("arena") ⇒
        arena(server, Int.MaxValue)
      case Array("arena", games) ⇒
        arena(server, parseInt(games))
      case Array("training", turns) ⇒
        training(server, _.training(parseInt(turns)))
      case Array("training", turns, map) ⇒
        training(server, _.training(parseInt(turns), Some(map)))
      case a ⇒
        log.error("Invalid arguments: {}. Game cannot be started.", a.mkString(" "))
    }
  }

  def arena(server: VindiniumClient, games: Int) {
    @annotation.tailrec
    def oneGame(it: Int) {
      log.info("[{}/{}] Waiting for pairing...", it, games)

      val input = server.arena
      log.info("[{}/{}] Starting arena game", it, games)
      log.info("Game's url: {}", input.viewUrl)

      openGameInBrowser(input)

      steps(server, input)

      log.info("[{}/{}] Finished arena game", it, games)
      log.info("Game's url: {}", input.viewUrl)

      if (it < games) oneGame(it + 1)
    }
    failsafe {
      oneGame(1)
    }
  }

  def training(server: VindiniumClient, boot: VindiniumClient ⇒ Input) {
    failsafe {
      val input = boot(server)
      log.info("Training game {}", input.viewUrl)

      openGameInBrowser(input)

      steps(server, input)
      log.info("Finished trainging game {}", input.viewUrl)
    }
  }

  private def showResult(input: Input): Unit = {
    log.info("*** Results ***")
    input.game.ranking.foreach { case (position, hero) =>
      val highlighting = if (hero.id == input.hero.id) " <-------" else ""
      log.info(s"$position: ${hero.name}, \t\t gold: ${hero.gold} $highlighting")
    }
  }

  private def openGameInBrowser(input: Input): Unit =
    Option(System.getProperty("open_in_browser"))
    .filter(value => value == "true")
    .map { _ =>
      openUrlInBrowser(input.viewUrl)
        .flatMap(_ => focusWindowWithTitle(input.game.id))
        .andThen {
          case Success(_) => log.debug("Successfully opened game in browser")
          case Failure(exception: Throwable) => log.error("Could not open game in browser.", exception)
        }
    }

  def steps(server: VindiniumClient, input: Input) = {
    failsafe {
      val finalInput = step(server, input, bot = BigBadWolfBot())
      showResult(finalInput)
    }
  }

  def failsafe(action: ⇒ Unit) {
    try {
      action
    }
    catch {
      case e: scalaj.http.HttpStatusException ⇒ log.error(s"[${e.code}] ${e.body}", e)
      case e: Exception                       ⇒ log.error("An error occurred", e)
    }
  }

  @annotation.tailrec
  def step(server: VindiniumClient, input: Input, bot: Bot): Input = {
    if (!input.game.finished) {
      val (move, newBot) = bot.move(input)
      import input._
      log.debug("Hero - HP: {}, position: {}. Next move: {}", Int.box(hero.life), hero.pos, move)
      step(server, server.move(input.playUrl, move), newBot)
    } else { input }
  }

  def makeClient = (
    Option(System.getProperty("server")).getOrElse("http://vindinium.org/"),
    System.getProperty("key")
  ) match {
      case (_, null)  ⇒ Left("Specify the user key with -Dkey=mySecretKey")
      case (url, key) ⇒ Right(new VindiniumClient(url + "/api", key))
    }

}
