package com.kamilmarnik.talkerr.comment.domain;

import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto;
import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException;
import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException;
import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    final UserDto loggedUser = userFacade.getLoggedUser();
    checkIfUserCanAddComment(loggedUser);

    return commentRepository.save(Comment.fromDto(createComment(comment, loggedUser))).dto();
  }

  public void deleteComment(long commentId) throws CommentNotFoundException {
    final UserDto loggedUser = userFacade.getLoggedUser();
    if (canUserDeleteComment(commentId, loggedUser)) {
      commentRepository.deleteById(commentId);
    }
  }

  public List<CommentDto> getCommentsByPostId(long postId) {
    return commentRepository.findCommentsByPostIdOrderByCreatedOn(postId).stream()
        .map(Comment::dto).collect(Collectors.toList());
  }

  public void deleteCommentsByPostId(long postId) {
    commentRepository.deleteCommentsByPostId(postId);
  }

  public void deleteCommentsByPostIdIn(Set<Long> postsIds) {
    commentRepository.deleteCommentsByPostIdIn(postsIds);
  }

  private void checkIfUserCanAddComment(UserDto user) throws UserRoleException {
    if (!userFacade.isAdminOrRegistered(user)) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new comment");
    }
  }

  private boolean canUserDeleteComment(long commentId, UserDto user) throws CommentNotFoundException {
    final CommentDto comment = getComment(commentId);
    return user.getUserId() == comment.getAuthorId() && userFacade.isAdminOrRegistered(user);
  }

  private CommentDto createComment(CreateCommentDto comment, UserDto author) {
    return CommentDto.builder()
        .content(comment.getContent())
        .createdOn(LocalDateTime.now())
        .postId(comment.getPostId())
        .authorId(author.getUserId())
        .authorLogin(author.getLogin())
        .build();
  }
}
