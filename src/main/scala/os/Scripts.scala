package os

import scala.annotation.tailrec
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.language.postfixOps
import scala.sys.process._

object Scripts {
  private val bashSuccess = 0
  private val defaultWaitingInterval = 200 milliseconds

  def openUrlInBrowser(url: String)(implicit ec: ExecutionContext): Future[Unit] = {
    val promise = Promise[Unit]
    Future {
      val logger = StringBuilder.newBuilder
      s"chromium-browser $url" ! ProcessLogger(logger.append(_)) match {
        case `bashSuccess` => promise.success()
        case _ => promise.failure(new RuntimeException(s"Cannot open game in chromium browser. ${logger.mkString}"))
      }
    }
    promise.future
  }

  def focusWindowWithTitle(title: String)(implicit ec: ExecutionContext): Future[Unit] = {
    val promise = Promise[Unit]

    Future {
      waitUntilWindowAppears(title, atMost = 2 seconds)
      focusWindowWhichContains(title)
      promise.success()
    }

    promise.future
  }

  private def isWindowOpened(title: String): Boolean = (s"wmctrl -l" #| s"grep $title" !) == bashSuccess

  private def focusWindowWhichContains(title: String) = s"wmctrl -a $title" !

  @tailrec
  private def waitUntilWindowAppears(title: String, atMost: FiniteDuration, interval: FiniteDuration = defaultWaitingInterval): Unit = {
    if(interval > (0 milliseconds) && !isWindowOpened(title)) {
      Thread.sleep(interval.toMillis)
      waitUntilWindowAppears(title, atMost - interval)
    }
  }
}
