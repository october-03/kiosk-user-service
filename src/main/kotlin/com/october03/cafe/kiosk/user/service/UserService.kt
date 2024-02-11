package com.october03.cafe.kiosk.user.service

import com.october03.cafe.kiosk.user.repository.User
import com.october03.cafe.kiosk.user.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
  fun getAllUsers(): List<User> {
    return userRepository.findAll()
  }

  fun getUserByEmail(email: String): User? {
    return userRepository.findByEmail(email)
  }

  fun createUser(req: UserCreateDto): User {
    val hashedPassword = BCryptPasswordEncoder().encode(req.password)

    val user = User(
      name = req.name,
      email = req.email,
      password = hashedPassword,
      phoneNumber = req.phoneNumber,
      fcmToken = req.fcmToken,
      authToken = req.authToken
    )

    userRepository.save(user)
    return user
  }

  fun login(req: UserLoginDto) {
    val user = getUserByEmail(req.email)
    println(user)
    if (user != null) {
      val passwordMatches = BCryptPasswordEncoder().matches(req.password, user.password)
      if (passwordMatches) {
        println("User logged in")
      } else {
        println("Password does not match")
      }
    } else {
      println("User not found")
    }
  }
}

data class UserCreateDto(
  val name: String = "",
  val email: String = "",
  val fcmToken: String = "",
  val phoneNumber: String = "",
  val password: String = "",
  val authToken: String = ""
)

data class UserLoginDto(
  val email: String = "",
  val password: String = ""
)