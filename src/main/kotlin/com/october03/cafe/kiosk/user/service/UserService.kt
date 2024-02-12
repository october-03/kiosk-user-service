package com.october03.cafe.kiosk.user.service

import com.october03.cafe.kiosk.user.config.TokenProvider
import com.october03.cafe.kiosk.user.repository.User
import com.october03.cafe.kiosk.user.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
  private val userRepository: UserRepository,
  private val tokenProvider: TokenProvider,
  private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {
  fun getAllUsers(): List<User> {
    return userRepository.findAll()
  }

  fun getUserByEmail(email: String): User? {
    return userRepository.findByEmail(email)
  }

  fun getUserByAuthToken(authToken: String): User? {
    return userRepository.findByAuthToken(authToken)
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

  fun login(req: UserLoginDto): LoginResponse? {
    val user = getUserByAuthToken(req.authToken)
    if (user != null) {
      val passwordMatches = BCryptPasswordEncoder().matches(req.password, user.password)
      if (passwordMatches) {
        try {
          val authenticationToken = UsernamePasswordAuthenticationToken(req.authToken, req.password)
          val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

          val accessToken = tokenProvider.createToken(authentication)

          val result = LoginResponse(user, accessToken)

          return result
        } catch (e: Exception) {
          println("Login Exception: $e")
        }
      } else {
        println("Password does not match")
      }
    } else {
      println("User not found")
    }
    return null
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
  val authToken: String = "",
  val password: String = ""
)

data class LoginResponse(
  val user: User,
  val accessToken: String
)