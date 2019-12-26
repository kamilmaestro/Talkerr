package com.kamilmarnik.talkerr.security.jwt;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
  final static String AUTHORITIES = "authorities";
  final static String AUTHORITY = "authority";
  final static String AUTHORIZATION_HEADER = "Authorization";

  String secretKey;
  String tokenPrefix;
  int tokenExpirationAfterMinutes;

  public JwtConfig() {
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public void setTokenPrefix(String tokenPrefix) {
    this.tokenPrefix = tokenPrefix;
  }

  public int getTokenExpirationAfterMinutes() {
    return tokenExpirationAfterMinutes;
  }

  public void setTokenExpirationAfterMinutes(int tokenExpirationAfterMinutes) {
    this.tokenExpirationAfterMinutes = tokenExpirationAfterMinutes;
  }
}
