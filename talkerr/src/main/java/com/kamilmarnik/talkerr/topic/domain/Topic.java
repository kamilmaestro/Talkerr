package com.kamilmarnik.talkerr.topic.domain;

import com.kamilmarnik.talkerr.topic.dto.TopicDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "topic")
class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Column(name = "topic_id")
  Long topicId;

  @NotNull
  @Getter
  @Column(name = "name", length = 30)
  String name;

  @NotNull
  @Column(name = "description", length = 100)
  String description;

  @Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
  LocalDateTime createdOn;

  @NotNull
  @Column(name = "creator_id")
  Long creatorId;

  static Topic fromDto(TopicDto dto) {
    return Topic.builder()
        .topicId(dto.getTopicId())
        .name(dto.getName())
        .description(dto.getDescription())
        .createdOn(dto.getCreatedOn())
        .creatorId(dto.getCreatorId())
        .build();
  }

  TopicDto dto() {
    return TopicDto.builder()
        .topicId(topicId)
        .name(name)
        .description(description)
        .createdOn(createdOn)
        .creatorId(creatorId)
        .build();
  }
}
