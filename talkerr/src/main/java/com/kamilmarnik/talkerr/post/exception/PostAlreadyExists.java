package com.kamilmarnik.talkerr.post.exception;

public class PostAlreadyExists extends RuntimeException {
  public PostAlreadyExists(String message) {
    super(message);
  }
}
