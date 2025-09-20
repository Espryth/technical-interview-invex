package me.javierca.interviews.invex.config.auth;

import lombok.AllArgsConstructor;
import me.javierca.interviews.invex.service.TokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  @Override
  protected void doFilterInternal(
      final @NotNull HttpServletRequest request,
      final @NotNull HttpServletResponse response,
      final @NotNull FilterChain filterChain
  ) throws ServletException, IOException {
    final var bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    final var token = this.tokenService.getToken(bearerToken.substring(7));
    if (token == null || token.isExpired()) {
      filterChain.doFilter(request, response);
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(new DummyAuthentication());
    filterChain.doFilter(request, response);
  }
}
