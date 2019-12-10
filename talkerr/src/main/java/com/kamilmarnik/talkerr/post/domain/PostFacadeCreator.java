package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.comment.domain.CommentFacade;
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

  @Autowired
  CommentFacade commentFacade;

  PostFacade createPostFacade(PostRepository postRepository, UserFacade userFacade, CommentFacade commentFacade) {
    return PostFacade.builder()
        .postRepository(postRepository)
        .commentFacade(commentFacade)
        .userFacade(userFacade)
        .build();
  }

  @Bean
  PostFacade createPostFacade() {
    return createPostFacade(postRepository, userFacade, commentFacade);
  }
}
