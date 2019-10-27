package com.kamilmarnik.talkerr.topic.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopicDto {
  Long topicId;
  String name;
  LocalDateTime createdOn;
  long creatorId;
}
