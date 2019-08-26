package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.UserAlreadyExistsException;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacade {

  UserRepository userRepository;

  public UserDto registerUser(UserDto user) throws UserAlreadyExistsException {
    Optional<User> savedUserById = userRepository.findById(user.getUserId());
    Optional<User> savedUserByLogin = userRepository.findUserByLogin(user.getLogin());

    if(savedUserById.isPresent() || savedUserByLogin.isPresent()) {
      throw new UserAlreadyExistsException("Can not register user: " + user.getLogin());
    }

    return userRepository.save(User.fromDto(user)).dto();
  }

  public UserDto getUser(long userId) throws UserNotFoundException {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Can not find user with id: " + userId))
        .dto();
  }
}
