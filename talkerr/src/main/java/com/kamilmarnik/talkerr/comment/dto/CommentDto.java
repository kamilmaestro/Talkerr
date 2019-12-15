package com.kamilmarnik.talkerr.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class CommentDto {
  long commentId;
  String content;
  LocalDateTime createdOn;
  long postId;
  long authorId;
}
