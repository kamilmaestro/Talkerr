package com.kamilmarnik.talkerr.comment.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCommentContentException extends RuntimeException {
  public InvalidCommentContentException(String message) {
    super(message);
  }
}
