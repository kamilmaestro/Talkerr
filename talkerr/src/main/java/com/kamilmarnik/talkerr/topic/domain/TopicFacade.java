package com.kamilmarnik.talkerr.topic.domain;

import com.kamilmarnik.talkerr.topic.dto.TopicDto;
import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopicFacade {

  TopicRepository topicRepository;
  UserFacade userFacade;

  public TopicDto addTopic(TopicDto topic) {
    return topicRepository.save(Topic.fromDto(topic)).dto();
  }

  public TopicDto getTopic(long topicId) throws TopicNotFoundException {
    return topicRepository.findById(topicId)
        .orElseThrow(() -> new TopicNotFoundException("Can not find topic with id: " + topicId))
        .dto();
  }
}
