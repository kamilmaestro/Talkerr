package com.kamilmarnik.talkerr.user.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserStatus {
  ADMIN,
  LOGGED
}
