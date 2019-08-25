package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacade {

  UserRepository userRepository;

  public UserDto addUser(UserDto user) {
    return userRepository.save(User.fromDto(user)).dto();
  }
}
