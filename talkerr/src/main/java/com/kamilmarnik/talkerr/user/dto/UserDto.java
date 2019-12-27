package com.kamilmarnik.talkerr.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public final class UserDto {
  public static final int MAX_LOG_LEN = 20;
  public static final int MIN_LOG_LEN = 4;
  public static final int MAX_PASS_LEN = 60;
  public static final int MIN_PASS_LEN = 6;

  Long userId;
  String login;
  UserStatusDto status;
  LocalDateTime registeredOn;
  String email;
}
