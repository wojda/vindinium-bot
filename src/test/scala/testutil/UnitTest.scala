package testutil

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

trait UnitTest extends FlatSpec with Matchers with CustomMatchers with TableDrivenPropertyChecks
