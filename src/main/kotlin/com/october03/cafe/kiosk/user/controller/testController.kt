package com.october03.cafe.kiosk.user.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class TestController {
  @RequestMapping("/test")
  fun test(): String {
    return "Hello World"
  }
}