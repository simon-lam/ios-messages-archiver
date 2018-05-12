package utils

import java.util.UUID

import org.scalatest.{FlatSpec, Matchers}

trait UnitSpec extends FlatSpec with Matchers {

  def uuid: String = UUID.randomUUID().toString
}