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
  public static final int MAX_NAME_LENGTH = 30;
  public static final int MAX_DESCRIPTION_LENGTH = 100;

  Long topicId;
  String name;
  String description;
  LocalDateTime createdOn;
  long authorId;
}
