package com.october03.cafe.kiosk.user.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.annotation.Order
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Order(0)
@Component
class JwtAuthenticationFilter(
  private val tokenProvider: TokenProvider
): GenericFilterBean() {
  override fun doFilter(req:  ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
    val token = resolveToken(req as HttpServletRequest)

    if (token != null && tokenProvider.validateToken(token)) {
      val authentication = tokenProvider.getAuthentication(token)
      req.setAttribute("authToken", (authentication.principal as UserDetails).username)
      SecurityContextHolder.getContext().authentication = authentication
    }

    chain?.doFilter(req, res)
  }

  private fun resolveToken(req: HttpServletRequest): String? {
    val bearerToken = req.getHeader("Authorization")
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7)
    }
    return null
  }
}