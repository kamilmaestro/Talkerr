package com.kamilmarnik.talkerr.user.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFacadeCreator {

  @Autowired
  UserRepository userRepository;

  public UserFacade createUserFacade(UserRepository userRepository) {
    return UserFacade.builder()
        .userRepository(userRepository)
        .build();
  }

  @Bean
  public UserFacade createUserFacade() {
    return createUserFacade(userRepository);
  }
}
