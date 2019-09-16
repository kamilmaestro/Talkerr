package com.kamilmarnik.talkerr.user.exception;

public class LoggedUserNotFoundException extends Exception {
  public LoggedUserNotFoundException(String message) {
    super(message);
  }
}
