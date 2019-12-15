package com.kamilmarnik.talkerr.topic.domain;

import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicFacadeCreator {

  @Autowired
  TopicRepository topicRepository;

  @Autowired
  UserFacade userFacade;

  @Autowired
  PostFacade postFacade;

  public TopicFacade createTopicFacade(TopicRepository topicRepository, UserFacade userFacade, PostFacade postFacade) {
    return TopicFacade.builder()
        .topicRepository(topicRepository)
        .userFacade(userFacade)
        .postFacade(postFacade)
        .build();
  }

  @Bean
  public TopicFacade createTopicFacade() {
    return createTopicFacade(topicRepository, userFacade, postFacade);
  }
}
