package com.october03.cafe.kiosk.user.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.NoArgsConstructor
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  val name: String,
  val email: String,
  val password: String,
  val phoneNumber: String,
  val authToken: String,
  val fcmToken: String,
  val createdAt: LocalDateTime? = null,
  val updatedAt: LocalDateTime? = null
) {
  constructor() : this(
    null,
    "",
    "",
    "",
    "",
    "",
    "",
    null,
    null
  )
}

interface UserRepository: JpaRepository<User, Long> {
  fun findByEmail(email: String): User?
}