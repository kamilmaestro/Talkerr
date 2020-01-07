package com.kamilmarnik.talkerr.security.auth;

import com.kamilmarnik.talkerr.mail.MailSender;
import com.kamilmarnik.talkerr.security.auth.requests.RegistrationRequest;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import com.kamilmarnik.talkerr.user.exception.UserAlreadyExistsException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
class AuthController {

  UserFacade userFacade;
  MailSender mailSender;

  @Autowired
  AuthController(@Autowired UserFacade userFacade, @Autowired MailSender mailSender) {
    this.userFacade = userFacade;
    this.mailSender = mailSender;
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationRequest user) throws UserAlreadyExistsException, InvalidLoginException, InvalidPasswordException {
    UserDto registeredUser = userFacade.registerUser(user);
    mailSender.sendRegistrationConfirmationMail(user.getEmail(), user.getUsername());

    return ResponseEntity.ok(registeredUser);
  }
}
