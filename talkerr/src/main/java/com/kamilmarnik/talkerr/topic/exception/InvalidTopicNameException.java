package com.kamilmarnik.talkerr.topic.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidTopicNameException extends RuntimeException {
  public InvalidTopicNameException(String message) {
    super(message);
  }
}
