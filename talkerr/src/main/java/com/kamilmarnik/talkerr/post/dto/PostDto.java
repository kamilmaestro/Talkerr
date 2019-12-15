package com.kamilmarnik.talkerr.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class PostDto {
  Long postId;
  String content;
  LocalDateTime createdOn;
  long authorId;
  long topicId;

  public boolean exists() {
    return postId != null && StringUtils.isNotBlank(content) && createdOn != null;
  }
}
