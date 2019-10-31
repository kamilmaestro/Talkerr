package com.kamilmarnik.talkerr.topic;

import com.kamilmarnik.talkerr.topic.domain.TopicFacade;
import com.kamilmarnik.talkerr.topic.dto.CreateTopicDto;
import com.kamilmarnik.talkerr.topic.dto.TopicDto;
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicContentException;
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
  ResponseEntity<TopicDto> addTopic(@RequestBody CreateTopicDto createTopic) throws InvalidTopicContentException, UserRoleException, TopicAlreadyExistsException {
    TopicDto topic = topicFacade.addTopic(createTopic);

    return ResponseEntity.ok(topic);
  }

  @GetMapping("/")
  ResponseEntity<List<TopicDto>> getTopics() {
    List<TopicDto> topics = topicFacade.getTopics();

    return ResponseEntity.ok(topics);
  }
}
