package testutil

import org.scalatest.{FlatSpec, Matchers}

trait UnitTest extends FlatSpec with Matchers with CustomMatchers
