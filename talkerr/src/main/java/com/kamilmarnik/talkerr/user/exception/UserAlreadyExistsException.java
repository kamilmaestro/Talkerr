package com.kamilmarnik.talkerr.user.exception;

public class UserAlreadyExistsException extends Exception {
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
