package com.kamilmarnik.talkerr.topic.domain;

import com.kamilmarnik.talkerr.topic.dto.CreateTopicDto;
import com.kamilmarnik.talkerr.topic.dto.TopicDto;
import com.kamilmarnik.talkerr.topic.exception.TopicAlreadyExistsException;
import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopicFacade {

  TopicRepository topicRepository;
  UserFacade userFacade;

  public TopicDto addTopic(CreateTopicDto topic) throws UserRoleException, TopicAlreadyExistsException {
    Objects.requireNonNull(topic);
    UserDto user = userFacade.getLoggedUser();
    checkIfUserCanAddTopic(user);
    checkIfTopicAlreadyExists(topic);

    return topicRepository.save(Topic.fromDto(createTopic(topic, user.getUserId()))).dto();
  }

  public TopicDto getTopic(long topicId) throws TopicNotFoundException {
    return topicRepository.findById(topicId)
        .orElseThrow(() -> new TopicNotFoundException("Can not find topic with id: " + topicId))
        .dto();
  }

  private void checkIfUserCanAddTopic(UserDto user) throws UserRoleException {
    if(!userFacade.isAdmin(user)) {
      throw new UserRoleException("User with username: " + user.getLogin() + "does not have a permission to add a new topic");
    }
  }

  private void checkIfTopicAlreadyExists(CreateTopicDto topic) throws TopicAlreadyExistsException {
    Optional<Topic> foundTopic = topicRepository.findByName(topic.getName());
    if(foundTopic.isPresent()) {
      throw new TopicAlreadyExistsException("Topic: " + topic.getName() + " already exists!");
    }
  }

  private TopicDto createTopic(CreateTopicDto topic, long userId) {
    return TopicDto.builder()
        .name(topic.getName())
        .createdOn(LocalDateTime.now())
        .creatorId(userId)
        .build();
  }
}
