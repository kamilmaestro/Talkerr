package com.kamilmarnik.talkerr.security.auth.requests;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class RegistrationRequest {
  String username;
  String password;
  String email;
}
