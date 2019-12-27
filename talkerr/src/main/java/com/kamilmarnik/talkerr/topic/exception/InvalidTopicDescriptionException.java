package com.kamilmarnik.talkerr.topic.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidTopicDescriptionException extends RuntimeException {
  public InvalidTopicDescriptionException(String message) {
    super(message);
  }
}
