package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Column(name = "user_id")
  Long userId;

  @Getter
  @NotNull
  @Column(name = "username")
  @Size(min = UserDto.MIN_LOG_LEN, max = UserDto.MAX_LOG_LEN)
  String login;

  @Setter(value = AccessLevel.PACKAGE)
  @Getter
  @NotNull
  @Column(name = "password")
  @Size(min = UserDto.MIN_PASS_LEN, max = UserDto.MAX_PASS_LEN)
  String password;

  @Getter
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "user_status")
  @Builder.Default
  UserStatusDto status = UserStatusDto.GUEST;

  @Column(name = "registered_on", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
  LocalDateTime registeredOn;

  @NotNull
  @Email(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")
  String email;

  static User fromDto(UserDto dto) {
    return User.builder()
        .userId(dto.getUserId())
        .login(dto.getLogin())
        .status(UserStatusDto.valueOf(dto.getStatus().name()))
        .registeredOn(dto.getRegisteredOn())
        .email(dto.getEmail())
        .build();
  }

  public UserDto dto() {
    return UserDto.builder()
        .userId(userId)
        .login(login)
        .status(UserStatusDto.valueOf(status.name()))
        .registeredOn(registeredOn)
        .email(email)
        .build();
  }
}
