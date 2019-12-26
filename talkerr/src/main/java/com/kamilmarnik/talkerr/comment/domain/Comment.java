package com.kamilmarnik.talkerr.comment.domain;

import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  @Getter(AccessLevel.PACKAGE)
  Long commentId;

  @NotNull
  @Column(name = "content", length = CommentDto.MAX_CONTENT_LENGTH)
  String content;

  @Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
  @Getter(AccessLevel.PACKAGE)
  LocalDateTime createdOn;

  @NotNull
  @Column(name = "post_id")
  @Getter(AccessLevel.PACKAGE)
  Long postId;

  @NotNull
  @Column(name = "author_id")
  Long authorId;

  @Getter
  @NotNull
  @Column(name = "author_login", length = UserDto.MAX_LOG_LEN)
  String authorLogin;

  static Comment fromDto(CommentDto dto) {
    return Comment.builder()
        .commentId(dto.getCommentId())
        .content(dto.getContent())
        .createdOn(dto.getCreatedOn())
        .postId(dto.getPostId())
        .authorId(dto.getAuthorId())
        .authorLogin(dto.getAuthorLogin())
        .build();
  }

  CommentDto dto() {
    return CommentDto.builder()
        .commentId(commentId)
        .content(content)
        .createdOn(createdOn)
        .postId(postId)
        .authorId(authorId)
        .authorLogin(authorLogin)
        .build();
  }
}
