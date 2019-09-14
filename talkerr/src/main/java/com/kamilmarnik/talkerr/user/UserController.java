package com.kamilmarnik.talkerr.user;

import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.LoggedUserDto;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import com.kamilmarnik.talkerr.user.exception.UserAlreadyExistsException;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
class UserController {

  UserFacade userFacade;

  @Autowired
  UserController(@Autowired UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
    try {
      UserDto user = userFacade.getUser(userId);
      return ResponseEntity.ok(user);
    } catch (UserNotFoundException e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<UserDto> registerUser(@RequestBody LoggedUserDto user) {
    try {
      UserDto registeredUser = userFacade.registerUser(user);
      return ResponseEntity.ok(registeredUser);
    } catch (UserAlreadyExistsException | InvalidLoginException | InvalidPasswordException e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

}