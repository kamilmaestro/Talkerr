package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.topic.domain.TopicFacade;
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
  TopicFacade topicFacade;

  PostFacade createPostFacade(PostRepository postRepository, UserFacade userFacade, TopicFacade topicFacade) {
    return PostFacade.builder()
        .postRepository(postRepository)
        .userFacade(userFacade)
        .topicFacade(topicFacade)
        .build();
  }

  @Bean
  PostFacade createPostFacade() {
    return createPostFacade(postRepository, userFacade, topicFacade);
  }
}
