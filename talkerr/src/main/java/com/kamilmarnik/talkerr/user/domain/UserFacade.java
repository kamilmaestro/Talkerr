package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter;
import com.kamilmarnik.talkerr.security.auth.requests.RegistrationRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacade {

  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  LoggedUserGetter loggedUserGetter;

  public UserDto registerUser(RegistrationRequest user) throws UserAlreadyExistsException, InvalidLoginException, InvalidPasswordException {
    Objects.requireNonNull(user);
    checkIfUserAlreadyExists(user.getUsername());
    LoginAndPasswordVerifier.verifyRegisteredLogAndPass(user.getUsername(), user.getPassword());

    return userRepository.save(createUser(user)).dto();
  }

  public UserDto getUser(long userId) throws UserNotFoundException {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Can not find user with Id: " + userId))
        .dto();
  }

  public UserDto getLoggedUser()  {
    return userRepository.findUserByLogin(loggedUserGetter.getLoggedUserName())
        .orElseThrow(() -> new UsernameNotFoundException("Can not find logged in user"))
        .dto();
  }

  public boolean isAdminOrRegistered(UserDto user) {
    return isAdmin(user) || isRegistered(user);
  }

  public boolean isAdmin(UserDto user) {
    return UserStatusDto.ADMIN.equals(user.getStatus());
  }

  private boolean isRegistered(UserDto user) {
    return UserStatusDto.REGISTERED.equals(user.getStatus());
  }

  private void checkIfUserAlreadyExists(String login) throws UserAlreadyExistsException {
    Optional<User> savedUser = userRepository.findUserByLogin(login);
    if(savedUser.isPresent()) {
      throw new UserAlreadyExistsException("Such user is already registered");
    }
  }

  private User createUser(RegistrationRequest user) {
    return User.builder()
        .login(user.getUsername())
        .password(passwordEncoder.encode(user.getPassword()))
        .status(UserStatusDto.REGISTERED)
        .registeredOn(LocalDateTime.now())
        .email(user.getEmail())
        .build();
  }
}
