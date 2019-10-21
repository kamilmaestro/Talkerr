package com.kamilmarnik.talkerr.logic.errors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ErrorResponse {
  private static final String MESSAGE_NOT_AVAILABLE = "Message not available!";

  @Getter
  int status;
  String error;
  String message;
  LocalDateTime date;

  ErrorResponse(int status, String error, String message) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.date = LocalDateTime.now();
  }

  ErrorResponse(int status, String error) {
    this.status = status;
    this.error = error;
    this.message = MESSAGE_NOT_AVAILABLE;
    this.date = LocalDateTime.now();
  }
}
