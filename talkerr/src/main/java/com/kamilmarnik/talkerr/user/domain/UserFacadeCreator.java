package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.logic.LoggedUserGetter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFacadeCreator {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  LoggedUserGetter loggedUserGetter;

  public UserFacade createUserFacade(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggedUserGetter loggedUserGetter) {
    return UserFacade.builder()
        .userRepository(userRepository)
        .passwordEncoder(passwordEncoder)
        .loggedUserGetter(loggedUserGetter)
        .build();
  }

  @Bean
  public UserFacade createUserFacade() {
    return createUserFacade(userRepository, passwordEncoder, loggedUserGetter);
  }
}
