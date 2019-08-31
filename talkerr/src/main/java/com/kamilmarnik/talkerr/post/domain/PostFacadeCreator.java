package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.user.domain.UserFacade;
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
  UserFacade userFacade;

  PostFacade createPostFacade(PostRepository postRepository, UserFacade userFacade) {
    return PostFacade.builder()
        .postRepository(postRepository)
        .userFacade(userFacade)
        .build();
  }

  @Bean
  PostFacade createPostFacade() {
    return createPostFacade(postRepository, userFacade);
  }
}
