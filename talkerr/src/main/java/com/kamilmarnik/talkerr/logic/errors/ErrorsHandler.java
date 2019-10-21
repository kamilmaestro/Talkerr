package com.kamilmarnik.talkerr.logic.errors;

import com.kamilmarnik.talkerr.post.exception.PostAlreadyExists;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ErrorsHandler {

  @ExceptionHandler(PostAlreadyExists.class)
  public ResponseEntity<ErrorResponse> handlePostAlreadyExists(PostAlreadyExists e) {
    return error(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException e) {
    return error(HttpStatus.NOT_FOUND, e.getMessage());
  }

  private ResponseEntity<ErrorResponse> error(HttpStatus status, String message) {
    ErrorResponse errResponse = new ErrorResponse(status.value(), status.toString(), message);
    return new ResponseEntity<>(errResponse, status);
  }
}
