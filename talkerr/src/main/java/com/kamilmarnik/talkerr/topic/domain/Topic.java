package com.kamilmarnik.talkerr.topic.domain;

import com.kamilmarnik.talkerr.topic.dto.TopicDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Column(name = "topic_id")
  Long topicId;

  @NotNull
  @Column(name = "name")
  String name;

  @Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
  LocalDateTime createdOn;

  @NotNull
  @Column(name = "creator_id")
  Long creatorId;

  static Topic fromDto(TopicDto dto) {
    return Topic.builder()
        .topicId(dto.getTopicId())
        .name(dto.getName())
        .createdOn(dto.getCreatedOn())
        .creatorId(dto.getCreatorId())
        .build();
  }

  TopicDto dto() {
    return TopicDto.builder()
        .topicId(topicId)
        .name(name)
        .createdOn(createdOn)
        .creatorId(creatorId)
        .build();
  }
}
