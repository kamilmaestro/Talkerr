package com.kamilmarnik.talkerr.topic.domain;

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

  public TopicFacade createTopicFacade(TopicRepository topicRepository, UserFacade userFacade) {
    return TopicFacade.builder()
        .topicRepository(topicRepository)
        .userFacade(userFacade)
        .build();
  }

  @Bean
  public TopicFacade createTopicFacade() {
    return createTopicFacade(topicRepository, userFacade);
  }
}
