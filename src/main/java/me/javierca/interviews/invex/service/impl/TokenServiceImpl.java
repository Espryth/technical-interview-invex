package me.javierca.interviews.invex.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.javierca.interviews.invex.model.token.Token;
import me.javierca.interviews.invex.repository.TokenRepository;
import me.javierca.interviews.invex.service.TokenService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

  private final TokenRepository tokenRepository;

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long expiration;

  TokenServiceImpl(final TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @Override
  public @NotNull Token generateToken() {
    final var token = Token.builder()
        .token(
            Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey)))
                .compact()
        )
        .build();
    return this.tokenRepository.save(token);
  }

  @Override
  @Transactional
  public @Nullable Token getToken(@NotNull String rawToken) {
    return this.tokenRepository.findByTokenAndExpiredFalse(rawToken)
        .map(token -> {
          if (this.isExpired(token.getToken())) {
            token.setExpired(true);
            return null;
          }
          return token;
        })
        .orElse(null);
  }

  private boolean isExpired(final String token) {
    try {
      return this.getClaims(token)
          .getExpiration()
          .before(new Date());
    } catch (final ExpiredJwtException e) {
      return true;
    }
  }

  private Claims getClaims(final String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey)))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
