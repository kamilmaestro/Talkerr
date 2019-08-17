package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@FieldDefaults(level = AccessLevel.PRIVATE)
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  Long userId;

  @Column(name = "login")
  String login;

  @Column(name = "password")
  String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_status")
  UserStatusDto status;

  static User fromDto(UserDto dto) {
    return User.builder()
        .userId(dto.getUserId())
        .login(dto.getLogin())
        .status(UserStatusDto.valueOf(dto.getStatus().name()))
        .build();
  }

  public UserDto dto() {
    return UserDto.builder()
        .userId(userId)
        .login(login)
        .status(UserStatusDto.valueOf(status.name()))
        .build();
  }
}
