package com.kamilmarnik.talkerr.logic.errors;

import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException;
import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException;
import com.kamilmarnik.talkerr.post.exception.InvalidPostContentException;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicDescriptionException;
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicNameException;
import com.kamilmarnik.talkerr.topic.exception.TopicAlreadyExistsException;
import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import com.kamilmarnik.talkerr.user.exception.LoggedUserNotFoundException;
import com.kamilmarnik.talkerr.user.exception.UserAlreadyExistsException;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ErrorAdvice {

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(PostNotFoundException e) {
    return error(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler(InvalidLoginException.class)
  public ResponseEntity<ErrorResponse> handleException(InvalidLoginException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<ErrorResponse> handleException(InvalidPasswordException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(LoggedUserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(LoggedUserNotFoundException e) {
    return error(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
    return error(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler(UserRoleException.class)
  public ResponseEntity<ErrorResponse> handleException(UserRoleException e) {
    return error(HttpStatus.UNAUTHORIZED, e);
  }

  @ExceptionHandler(TopicNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(TopicNotFoundException e) {
    return error(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler(TopicAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleException(TopicAlreadyExistsException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(InvalidTopicNameException.class)
  public ResponseEntity<ErrorResponse> handleException(InvalidTopicNameException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(InvalidTopicDescriptionException.class)
  public ResponseEntity<ErrorResponse> handleException(InvalidTopicDescriptionException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(CommentNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(CommentNotFoundException e) {
    return error(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler(InvalidPostContentException.class)
  public ResponseEntity<ErrorResponse> handleException(InvalidPostContentException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(InvalidCommentContentException.class)
  public ResponseEntity<ErrorResponse> handleException(InvalidCommentContentException e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  private ResponseEntity<ErrorResponse> error(HttpStatus status, Exception e) {
    return ResponseEntity.status(status).body(StringUtils.isBlank(e.getMessage()) ?
        new ErrorResponse(status, e.getClass().getSimpleName()) :
        new ErrorResponse(status, e.getClass().getSimpleName(), e.getMessage()));
  }
}
