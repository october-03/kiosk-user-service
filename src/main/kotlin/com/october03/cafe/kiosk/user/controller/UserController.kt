package com.october03.cafe.kiosk.user.controller

import com.october03.cafe.kiosk.user.repository.User
import com.october03.cafe.kiosk.user.service.LoginResponse
import com.october03.cafe.kiosk.user.service.UserCreateDto
import com.october03.cafe.kiosk.user.service.UserLoginDto
import com.october03.cafe.kiosk.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
  fun login(@RequestBody req: UserLoginDto): LoginResponse? {
    return userService.login(req)
  }

  @GetMapping("/auth-token/{authToken}")
  fun getUserByAuthToken(@PathVariable authToken: String): User? {
    return userService.getUserByAuthToken(authToken)
  }
}

