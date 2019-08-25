package com.kamilmarnik.talkerr.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class UserDto {
  Long userId;
  String login;
  String password;
  UserStatusDto status;
  Date createdOn;
}
