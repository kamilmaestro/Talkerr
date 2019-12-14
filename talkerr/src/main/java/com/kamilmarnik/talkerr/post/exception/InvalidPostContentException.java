package com.kamilmarnik.talkerr.post.exception;

public class InvalidPostContentException extends RuntimeException {
  public InvalidPostContentException(String message) {
    super(message);
  }
}
