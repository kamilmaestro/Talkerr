package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.user.domain.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
class PostFacadeCreator {

  @Autowired
  PostRepository postRepository;

  @Autowired
  UserRepository userRepository;

  PostFacade createPostFacade(PostRepository postRepository, UserRepository userRepository) {
    return PostFacade.builder()
        .postRepository(postRepository)
        .userRepository(userRepository)
        .build();
  }

  @Bean
  PostFacade createPostFacade() {
    return createPostFacade(postRepository, userRepository);
  }
}
