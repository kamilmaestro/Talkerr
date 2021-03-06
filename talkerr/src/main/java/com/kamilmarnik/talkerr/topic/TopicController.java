package com.kamilmarnik.talkerr.topic;

import com.kamilmarnik.talkerr.post.dto.CreatePostDto;
import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.topic.domain.TopicFacade;
import com.kamilmarnik.talkerr.topic.dto.CreateTopicDto;
import com.kamilmarnik.talkerr.topic.dto.TopicDto;
import com.kamilmarnik.talkerr.topic.exception.TopicAlreadyExistsException;
import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/topic")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@Slf4j
public class TopicController {

  TopicFacade topicFacade;

  @Autowired
  TopicController(@Autowired TopicFacade topicFacade) {
    this.topicFacade = topicFacade;
  }

  @GetMapping("/{topicId}")
  ResponseEntity<TopicDto> getTopic(@PathVariable Long topicId) throws TopicNotFoundException {
    TopicDto topic = topicFacade.getTopic(topicId);

    return ResponseEntity.ok(topic);
  }

  @PostMapping("/")
  ResponseEntity<TopicDto> addTopic(@RequestBody CreateTopicDto createTopic) throws UserRoleException, TopicAlreadyExistsException {
    TopicDto topic = topicFacade.addTopic(createTopic);

    return ResponseEntity.ok(topic);
  }

  @PostMapping("/post/")
  public ResponseEntity<PostDto> addPostToTopic(@RequestBody CreatePostDto post) throws UserRoleException, TopicNotFoundException {
    PostDto addedPost = topicFacade.addPostToTopic(post);

    return ResponseEntity.ok(addedPost);
  }

  @GetMapping("/")
  ResponseEntity<List<TopicDto>> getTopics() {
    List<TopicDto> topics = topicFacade.getTopics();

    return ResponseEntity.ok(topics);
  }

  @DeleteMapping("/{topicId}")
  ResponseEntity<?> deleteTopic(@PathVariable long topicId) throws TopicNotFoundException {
    topicFacade.deleteTopic(topicId);

    return ResponseEntity.ok().build();
  }
}
