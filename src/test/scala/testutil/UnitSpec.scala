package testutil

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

trait UnitSpec extends FlatSpec with Matchers with CustomMatchers with TableDrivenPropertyChecks
