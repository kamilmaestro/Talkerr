package com.kamilmarnik.talkerr.user.exception;

public class InvalidPasswordException extends Exception{
  public InvalidPasswordException(String message) {
    super(message);
  }
}
