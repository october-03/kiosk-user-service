package com.october03.cafe.kiosk.user.service

import com.october03.cafe.kiosk.user.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserService(
  private val userRepository: UserRepository,
): UserDetailsService {
  override fun loadUserByUsername(authToken: String): UserDetails =
    userRepository.findByAuthToken(authToken)
      ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException("해당 유저는 없습니다.")

  private fun createUserDetails(user: com.october03.cafe.kiosk.user.repository.User): UserDetails =
    CustomUser(
      user.id!!,
      user.authToken,
      user.password,
      listOf(SimpleGrantedAuthority("ROLE_USER"))
    )
}

class CustomUser(
  val userId: Long,
  userName: String,
  password: String,
  authorities: Collection<GrantedAuthority>
) : User(userName, password, authorities)