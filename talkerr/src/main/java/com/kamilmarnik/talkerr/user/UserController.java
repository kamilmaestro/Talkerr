package com.kamilmarnik.talkerr.user;

import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws UserNotFoundException {
      UserDto user = userFacade.getUser(userId);

      return ResponseEntity.ok(user);
  }
}
