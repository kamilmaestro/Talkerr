package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.logic.LoggedUserGetter;
import com.kamilmarnik.talkerr.logic.LoginAndPasswordVerifier;
import com.kamilmarnik.talkerr.user.dto.LoggedUserDto;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import com.kamilmarnik.talkerr.user.exception.UserAlreadyExistsException;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacade {

  UserRepository userRepository;

  public UserDto registerUser(LoggedUserDto user) throws UserAlreadyExistsException, InvalidLoginException, InvalidPasswordException {
    Objects.requireNonNull(user);
    checkIfUserExists(user.getLogin());
    LoginAndPasswordVerifier.verifyRegisteredLogAndPass(user.getLogin(), user.getPassword());

    return userRepository.save(createUser(user)).dto();
  }

  public UserDto getUser(long userId) throws UserNotFoundException {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Can not find user with Id: " + userId))
        .dto();
  }

  public UserDto getLoggedUserName() {
    return userRepository.findUserByLogin(LoggedUserGetter.getLoggedUserName())
        .orElseThrow(() -> new UsernameNotFoundException("Can not find user"))
        .dto();
  }

  private void checkIfUserExists(String login) throws UserAlreadyExistsException {
    Optional<User> savedUser = userRepository.findUserByLogin(login);
    if(savedUser.isPresent()) {
      throw new UserAlreadyExistsException("Such user is already registered");
    }
  }

  private User createUser(LoggedUserDto user) {
    return User.builder()
        .login(user.getLogin())
        .status(UserStatusDto.REGISTERED)
        .registeredOn(LocalDateTime.now())
        .build();
  }
}
