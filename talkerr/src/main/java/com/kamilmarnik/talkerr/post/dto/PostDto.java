package com.kamilmarnik.talkerr.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class PostDto {
  Long postId;
  String content;
  LocalDateTime createdOn;
  long userId;
}
