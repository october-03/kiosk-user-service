package com.october03.cafe.kiosk.user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
  private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
  @Bean
  open fun configure(http: HttpSecurity): SecurityFilterChain {
    http {
      httpBasic {
        disable()
      }
      csrf {
        disable()
      }
    }
    http.authorizeHttpRequests {
      it.requestMatchers("/register", "/login", "/auth-token/**").permitAll()
      it.anyRequest().authenticated()
    }
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    return http.build()
  }

  @Bean
  fun passwordEncoder(): BCryptPasswordEncoder {
    return BCryptPasswordEncoder()
  }
}