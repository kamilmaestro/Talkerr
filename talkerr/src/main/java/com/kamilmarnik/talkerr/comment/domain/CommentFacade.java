package com.kamilmarnik.talkerr.comment.domain;

import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto;
import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException;
import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException;
import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentFacade {

  CommentRepository commentRepository;
  UserFacade userFacade;
  PostFacade postFacade;

  public CommentDto getComment(long commentId) throws CommentNotFoundException {
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new CommentNotFoundException("Can not find a comment with id: " + commentId))
        .dto();
  }

  public CommentDto addComment(CreateCommentDto comment) throws UserRoleException, InvalidCommentContentException {
    UserDto loggedUser = userFacade.getLoggedUser();
    checkCommentContent(comment.getContent());
    checkIfUserCanAddComment(loggedUser);

    return commentRepository.save(Comment.fromDto(createComment(comment, loggedUser.getUserId()))).dto();
  }

  public void deleteComment(long commentId) throws CommentNotFoundException {
    UserDto loggedUser = userFacade.getLoggedUser();
    if(canUserDeleteComment(commentId, loggedUser)) {
      commentRepository.deleteById(commentId);
    }
  }

  public void deleteCommentsByPostId(long postId) {
    commentRepository.deleteCommentsByPostId(postId);
  }

  private void checkCommentContent(String commentContent) throws InvalidCommentContentException {
    Optional.ofNullable(commentContent)
        .filter(StringUtils::isNotBlank)
        .orElseThrow(() -> new InvalidCommentContentException("Can add comment with such content: " + commentContent));
  }

  private void checkIfUserCanAddComment(UserDto user) throws UserRoleException {
    if(user.getStatus() != UserStatusDto.REGISTERED && user.getStatus() != UserStatusDto.ADMIN) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new comment");
    }
  }

  private boolean canUserDeleteComment(long commentId, UserDto user) throws CommentNotFoundException {
    CommentDto comment = getComment(commentId);
    return user.getUserId() == comment.getAuthorId() && userFacade.isAdminOrRegistered(user);
  }

  private CommentDto createComment(CreateCommentDto comment, long authorId) {
    return CommentDto.builder()
        .content(comment.getContent())
        .createdOn(LocalDateTime.now())
        .postId(comment.getPostId())
        .authorId(authorId)
        .build();
  }
}
