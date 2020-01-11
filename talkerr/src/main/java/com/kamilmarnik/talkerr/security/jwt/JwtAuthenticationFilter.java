package com.kamilmarnik.talkerr.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  SecretKey secretKey;
  JwtConfig jwtConfig;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Optional<String> token = getToken(request.getHeader(JwtConfig.AUTHORIZATION_HEADER));
    if (!token.isPresent()) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      Jws<Claims> claimsJws = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token.get());

      Claims body = claimsJws.getBody();
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          body.getSubject(), null, getSimpleGrantedAuthorities(body)
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (JwtException e) {
      throw new IllegalStateException("Can not authenticate");
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> getToken(final String authorizationHeader) {
    return Optional.ofNullable(authorizationHeader)
        .filter(header -> !header.isEmpty() && header.startsWith(jwtConfig.getTokenPrefix()))
        .map(header -> header.replaceFirst(jwtConfig.getTokenPrefix(), ""));
  }

  private Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities(Claims body) {
    List<Map<String, String>> authorities = (List<Map<String, String>>) body.get(JwtConfig.AUTHORITIES);

    return authorities.stream()
        .map(auth -> new SimpleGrantedAuthority(auth.get(JwtConfig.AUTHORITY)))
        .collect(Collectors.toSet());
  }
}
