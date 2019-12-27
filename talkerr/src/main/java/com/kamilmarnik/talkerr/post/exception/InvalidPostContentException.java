package com.kamilmarnik.talkerr.post.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidPostContentException extends RuntimeException {
  public InvalidPostContentException(String message) {
    super(message);
  }
}
