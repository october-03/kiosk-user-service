package com.october03.cafe.kiosk.user.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
  @RequestMapping("/test")
  fun test(http: HttpServletRequest): String {
    return http.getAttribute("authToken") as String
  }
}