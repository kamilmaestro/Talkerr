package com.kamilmarnik.talkerr.comment.dto;

import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException;
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
public final class CommentDto {
  public static final int MAX_CONTENT_LENGTH = 500;

  long commentId;
  String content;
  LocalDateTime createdOn;
  long postId;
  long authorId;
  String authorLogin;

  public static CommentDtoBuilder builder() {
    return new CommentDtoVerifier();
  }

  private static class CommentDtoVerifier extends CommentDtoBuilder {
    @Override
    public CommentDto build() {
      Optional.ofNullable(super.content)
          .filter(c -> StringUtils.isNotBlank(c) && c.length() <= MAX_CONTENT_LENGTH)
          .orElseThrow(InvalidCommentContentException::new);

      return super.build();
    }
  }
}
