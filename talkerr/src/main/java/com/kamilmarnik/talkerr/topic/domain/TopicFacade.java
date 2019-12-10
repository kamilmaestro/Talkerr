package com.kamilmarnik.talkerr.topic.domain;

import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.post.dto.CreatePostDto;
import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.topic.dto.CreateTopicDto;
import com.kamilmarnik.talkerr.topic.dto.TopicDto;
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicContentException;
import com.kamilmarnik.talkerr.topic.exception.TopicAlreadyExistsException;
import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopicFacade {

  TopicRepository topicRepository;
  UserFacade userFacade;
  PostFacade postFacade;

  public TopicDto addTopic(CreateTopicDto topic) throws UserRoleException, TopicAlreadyExistsException, InvalidTopicContentException {
    UserDto user = userFacade.getLoggedUser();
    checkIfUserCanAddTopic(user, topic);

    return topicRepository.save(Topic.fromDto(createTopic(topic, user.getUserId()))).dto();
  }

  public TopicDto getTopic(long topicId) throws TopicNotFoundException {
    return topicRepository.findById(topicId)
        .orElseThrow(() -> new TopicNotFoundException("Can not find topic with id: " + topicId))
        .dto();
  }

  public List<TopicDto> getTopics() {
    return topicRepository.findAll().stream()
        .map(Topic::dto)
        .collect(Collectors.toList());
  }

  public PostDto addPostToTopic(CreatePostDto post) throws UserRoleException, TopicNotFoundException {
    Objects.requireNonNull(post, "Post can not be created due to invalid data");
    getTopic(post.getTopicId());

    return postFacade.addPost(post);
  }

  public void deleteTopic(long topicId) throws TopicNotFoundException {
    UserDto loggedUser = userFacade.getLoggedUser();
    getTopic(topicId);

    if(userFacade.isAdmin(loggedUser)) {
      topicRepository.deleteById(topicId);
      postFacade.deletePostsByTopicId(topicId);
    }
  }

  private void checkIfUserCanAddTopic(UserDto user, CreateTopicDto topic) throws UserRoleException, TopicAlreadyExistsException, InvalidTopicContentException {
    Objects.requireNonNull(topic, "Topic can not be created due to invalid data");
    checkIfTopicAlreadyExists(topic);
    checkTopicContent(topic);
    if(!userFacade.isAdmin(user)) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new topic");
    }
  }

  private void checkIfTopicAlreadyExists(CreateTopicDto topic) throws TopicAlreadyExistsException {
    Optional<Topic> foundTopic = topicRepository.findByName(topic.getName());
    if(foundTopic.isPresent()) {
      throw new TopicAlreadyExistsException("Topic: " + topic.getName() + " already exists!");
    }
  }

  private void checkTopicContent(CreateTopicDto topic) throws InvalidTopicContentException {
    if(topic.getName().length() > TopicDto.MAX_NAME_LENGTH) {
      throw new InvalidTopicContentException("Invalid length of name of topic");
    } else if(topic.getDescription().length() > TopicDto.MAX_DESCRIPTION_LENGTH) {
      throw new InvalidTopicContentException("Invalid length of description of topic");
    }
  }

  private TopicDto createTopic(CreateTopicDto topic, long authorId) {
    return TopicDto.builder()
        .name(topic.getName())
        .description(topic.getDescription())
        .createdOn(LocalDateTime.now())
        .authorId(authorId)
        .build();
  }
}
