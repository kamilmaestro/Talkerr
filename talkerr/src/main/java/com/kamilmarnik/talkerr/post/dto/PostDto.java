package com.kamilmarnik.talkerr.post.dto;

import com.kamilmarnik.talkerr.post.exception.InvalidPostContentException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public final class PostDto {
  public static final int MAX_CONTENT_LENGTH = 2000;

  Long postId;
  String content;
  LocalDateTime createdOn;
  long authorId;
  long topicId;
  String authorLogin;

  public static PostDtoBuilder builder() {
    return new PostDtoVerifier();
  }

  private static class PostDtoVerifier extends PostDtoBuilder {
    @Override
    public PostDto build() {
      Optional.ofNullable(super.content)
          .filter(c -> StringUtils.isNotBlank(c) && c.length() <= MAX_CONTENT_LENGTH)
          .orElseThrow(InvalidPostContentException::new);

      return super.build();
    }
  }
}
