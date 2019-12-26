package com.kamilmarnik.talkerr.topic.dto;

import com.kamilmarnik.talkerr.topic.exception.InvalidTopicDescriptionException;
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicNameException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopicDto {
  public static final int MAX_NAME_LENGTH = 30;
  public static final int MAX_DESCRIPTION_LENGTH = 100;

  Long topicId;
  String name;
  String description;
  LocalDateTime createdOn;
  long authorId;

  public static TopicDtoBuilder builder() {
    return new TopicDtoVerifier();
  }

  private static class TopicDtoVerifier extends TopicDtoBuilder {
    @Override
    public TopicDto build() {
      Optional.ofNullable(super.name)
          .filter(n -> StringUtils.isNotBlank(n) && n.length() <= MAX_NAME_LENGTH)
          .orElseThrow(InvalidTopicNameException::new);
      Optional.ofNullable(super.description)
          .filter(d -> StringUtils.isNotBlank(d) && d.length() <= MAX_DESCRIPTION_LENGTH)
          .orElseThrow(InvalidTopicDescriptionException::new);

      return super.build();
    }
  }
}
