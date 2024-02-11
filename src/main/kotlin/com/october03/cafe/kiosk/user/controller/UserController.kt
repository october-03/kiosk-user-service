package com.october03.cafe.kiosk.user.controller

import com.october03.cafe.kiosk.user.repository.User
import com.october03.cafe.kiosk.user.service.UserCreateDto
import com.october03.cafe.kiosk.user.service.UserLoginDto
import com.october03.cafe.kiosk.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
  @PostMapping("/register")
  fun register(@RequestBody req: UserCreateDto): User {
    return userService.createUser(req)
  }

  @PostMapping("/login")
  fun login(@RequestBody req: UserLoginDto) {
    return userService.login(req)
  }
}

