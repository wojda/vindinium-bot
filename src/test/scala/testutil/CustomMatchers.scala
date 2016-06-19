package testutil

import org.scalatest.matchers._

import scala.collection.GenTraversable

object CustomMatchers extends CustomMatchers

trait CustomMatchers {

  class ListWithDuplicatedElementsMatcher() extends Matcher[GenTraversable[Any]] {

    def apply(listToVerify: GenTraversable[Any]) = {
      val duplicatedElements = listToVerify.groupBy(identity).collect { case (x, List(_, _, _*)) => x }
      MatchResult(
        duplicatedElements.nonEmpty,
        s"List does not contain duplicated elements.",
        s"List contains duplicated elements. Duplicates: ${duplicatedElements.mkString(", ")}"
      )
    }
  }

  def containDuplicates = new ListWithDuplicatedElementsMatcher()
}
