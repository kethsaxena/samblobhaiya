package com.ps.helloworld

import org.scalatest.funsuite.AnyFunSuite

class AppTest extends AnyFunSuite {

  test("App should return the correct greeting") {
    assert(App.greet() == "Hello, World!")
  }
}
