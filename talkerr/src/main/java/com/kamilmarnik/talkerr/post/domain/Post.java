package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.PostDto;
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
@Table(name = "posts")
class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Column(name = "post_id")
  Long postId;

  @NotNull
  @Column(name = "content")
  String content;

  @NotNull
  @Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
  LocalDateTime createdOn;

  @NotNull
  @Column(name = "author_id")
  Long authorId;

  @NotNull
  @Column(name = "topic_id")
  Long topicId;

  static Post fromDto(PostDto dto) {
    return Post.builder()
        .postId(dto.getPostId())
        .content(dto.getContent())
        .createdOn(dto.getCreatedOn())
        .authorId(dto.getAuthorId())
        .topicId(dto.getTopicId())
        .build();
  }

  public PostDto dto() {
    return PostDto.builder()
        .postId(postId)
        .content(content)
        .createdOn(createdOn)
        .authorId(authorId)
        .topicId(topicId)
        .build();
  }
}
