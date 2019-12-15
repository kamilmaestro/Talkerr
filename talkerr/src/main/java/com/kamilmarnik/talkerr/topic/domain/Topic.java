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
@Table(name = "topics")
class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Column(name = "topic_id")
  Long topicId;

  @NotNull
  @Getter
  @Column(name = "name", length = TopicDto.MAX_NAME_LENGTH)
  String name;

  @NotNull
  @Column(name = "description", length = TopicDto.MAX_DESCRIPTION_LENGTH)
  String description;

  @Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
  LocalDateTime createdOn;

  @NotNull
  @Column(name = "author_id")
  Long authorId;

  static Topic fromDto(TopicDto dto) {
    return Topic.builder()
        .topicId(dto.getTopicId())
        .name(dto.getName())
        .description(dto.getDescription())
        .createdOn(dto.getCreatedOn())
        .authorId(dto.getAuthorId())
        .build();
  }

  TopicDto dto() {
    return TopicDto.builder()
        .topicId(topicId)
        .name(name)
        .description(description)
        .createdOn(createdOn)
        .authorId(authorId)
        .build();
  }
}
