package com.kamilmarnik.talkerr.post.domain;

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

  PostFacade createPostFacade(PostRepository postRepository) {
    return PostFacade.builder()
        .postRepository(postRepository)
        .build();
  }

  @Bean
  PostFacade createPostFacade() {
    return createPostFacade(postRepository);
  }
}
