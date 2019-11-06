package com.kamilmarnik.talkerr.user;

import com.kamilmarnik.talkerr.logic.Constants;
import com.kamilmarnik.talkerr.mail.MailSender;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.RegistrationRequest;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import com.kamilmarnik.talkerr.user.exception.UserAlreadyExistsException;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws UserNotFoundException {
      UserDto user = userFacade.getUser(userId);

      return ResponseEntity.ok(user);
  }

  @PostMapping("/")
  public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationRequest user) throws UserAlreadyExistsException, InvalidLoginException, InvalidPasswordException {
      UserDto registeredUser = userFacade.registerUser(user);
      MailSender mailSender = MailSender.builder()
          .userMail(Constants.TALKERR_MAIL)
          .password(Constants.TALKERR_MAIL_PASSWORD)
          .userName(user.getUsername())
          .build();
      mailSender.sendMail(user.getEmail());
      return ResponseEntity.ok(registeredUser);
  }

}
