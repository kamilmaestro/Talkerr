package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder(toBuilder = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;

  String password;

  UserStatus status;

  static User fromDto(UserDto dto) {
    return User.builder()
        .id(dto.getId())
        .name(dto.getName())
        .status(dto.getStatus())
        .build();
  }

  public UserDto dto() {
    return UserDto.builder()
        .id(id)
        .name(name)
        .status(status)
        .build();
  }
}
