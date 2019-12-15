package com.kamilmarnik.talkerr.comment.domain;

import com.kamilmarnik.talkerr.user.domain.UserFacade;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
class CommentFacadeCreator {

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  UserFacade userFacade;

  CommentFacade createCommentFacade(CommentRepository commentRepository, UserFacade userFacade) {
    return CommentFacade.builder()
        .commentRepository(commentRepository)
        .userFacade(userFacade)
        .build();
  }

  @Bean
  CommentFacade createCommentFacade() {
    return createCommentFacade(commentRepository, userFacade);
  }
}
