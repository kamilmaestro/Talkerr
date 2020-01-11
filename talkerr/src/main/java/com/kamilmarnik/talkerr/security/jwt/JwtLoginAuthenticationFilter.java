package com.kamilmarnik.talkerr.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilmarnik.talkerr.security.auth.requests.LoginRequest;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  AuthenticationManager authenticationManager;
  JwtConfig jwtConfig;
  SecretKey secretKey;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          loginRequest.getUsername(), loginRequest.getPassword()
      );
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
    String token = Jwts.builder()
        .setSubject(authResult.getName())
        .claim(JwtConfig.AUTHORITIES, authResult.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(Date.from(LocalDateTime.now().plusMinutes(jwtConfig.getTokenExpirationAfterMinutes()).atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(secretKey)
        .compact();

    response.addHeader(JwtConfig.AUTHORIZATION_HEADER, jwtConfig.getTokenPrefix() + token);
  }
}
