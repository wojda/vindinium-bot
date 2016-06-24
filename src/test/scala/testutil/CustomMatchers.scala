package testutil

import org.scalatest.matchers._

import scala.collection.GenTraversable

object CustomMatchers extends CustomMatchers

trait CustomMatchers {

  def containDuplicates: Matcher[GenTraversable[Any]] = new ListWithDuplicatedElementsMatcher()

  private class ListWithDuplicatedElementsMatcher() extends Matcher[GenTraversable[Any]] {

    def apply(listToVerify: GenTraversable[Any]) = {
      val duplicatedElements = listToVerify.groupBy(identity).collect { case (x, List(_, _, _*)) => x }
      MatchResult(
        duplicatedElements.nonEmpty,
        "List does not contain duplicated elements.",
        s"List contains duplicated elements. Duplicates: ${duplicatedElements.mkString(", ")}"
      )
    }
  }

}