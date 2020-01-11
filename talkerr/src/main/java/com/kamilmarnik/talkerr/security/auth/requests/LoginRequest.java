package com.kamilmarnik.talkerr.security.auth.requests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
  String username;
  String password;
}
