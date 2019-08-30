package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Column(name = "user_id")
  Long userId;

  @Column(name = "login")
  String login;

  @Column(name = "password")
  String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_status")
  UserStatusDto status;

  @Column(name = "created_on")
  Date createdOn;

  static User fromDto(UserDto dto) {
    return User.builder()
        .userId(dto.getUserId())
        .login(dto.getLogin())
        .password(dto.getPassword())
        .status(UserStatusDto.valueOf(dto.getStatus().name()))
        .createdOn(dto.getCreatedOn())
        .build();
  }

  public UserDto dto() {
    return UserDto.builder()
        .userId(userId)
        .login(login)
        .password(password)
        .status(UserStatusDto.valueOf(status.name()))
        .createdOn(createdOn)
        .build();
  }
}
