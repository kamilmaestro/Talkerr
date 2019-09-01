package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.LoginAndPasswordVerifier;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import com.kamilmarnik.talkerr.user.exception.UserAlreadyExistsException;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacade {

  UserRepository userRepository;

  public UserDto registerUser(UserDto user) throws UserAlreadyExistsException, InvalidLoginException, InvalidPasswordException {
    Objects.requireNonNull(user);
    Optional<User> savedUser = userRepository.findUserByLogin(user.getLogin());
    if(savedUser.isPresent()) {
      throw new UserAlreadyExistsException("Such user is already registered");
    }

    LoginAndPasswordVerifier.verifyLogAndPass(user.getLogin(), user.getPassword());

    return userRepository.save(User.fromDto(user)).dto();
  }

  public UserDto getUser(long userId) throws UserNotFoundException {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Can not find user with Id: " + userId))
        .dto();
  }
}
