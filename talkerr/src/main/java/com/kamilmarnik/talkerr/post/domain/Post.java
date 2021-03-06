package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.user.dto.UserDto;
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
  @Column(name = "content", length = PostDto.MAX_CONTENT_LENGTH)
  String content;

  @NotNull
  @Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
  LocalDateTime createdOn;

  @NotNull
  @Column(name = "author_id")
  Long authorId;

  @Getter
  @NotNull
  @Column(name = "topic_id")
  Long topicId;

  @Getter
  @NotNull
  @Column(name = "author_login", length = UserDto.MAX_LOG_LEN)
  String authorLogin;

  static Post fromDto(PostDto dto) {
    return Post.builder()
        .postId(dto.getPostId())
        .content(dto.getContent())
        .createdOn(dto.getCreatedOn())
        .authorId(dto.getAuthorId())
        .topicId(dto.getTopicId())
        .authorLogin(dto.getAuthorLogin())
        .build();
  }

  public PostDto dto() {
    return PostDto.builder()
        .postId(postId)
        .content(content)
        .createdOn(createdOn)
        .authorId(authorId)
        .topicId(topicId)
        .authorLogin(authorLogin)
        .build();
  }
}
