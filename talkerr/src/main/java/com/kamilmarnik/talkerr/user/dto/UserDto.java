package com.kamilmarnik.talkerr.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class UserDto {
  public static final int MAX_LOG_LEN = 20;
  public static final int MIN_LOG_LEN = 4;
  public static final int MAX_PASS_LEN = 20;
  public static final int MIN_PASS_LEN = 6;

  Long userId;
  String login;
  String password;
  UserStatusDto status;
  LocalDateTime registeredOn;
}
