package com.kamilmarnik.talkerr.comment.domain;

import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto;
import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException;
import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;

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

  public CommentDto addComment(CreateCommentDto comment) throws PostNotFoundException, UserRoleException {
    UserDto loggedUser = userFacade.getLoggedUser();
    checkIfUserCanAddComment(comment, loggedUser);

    return commentRepository.save(Comment.fromDto(createComment(comment, loggedUser.getUserId()))).dto();
  }

  private void checkIfUserCanAddComment(CreateCommentDto comment, UserDto user) throws PostNotFoundException, UserRoleException {
    Objects.requireNonNull(comment, "Comment can not be created due to invalid data");
    checkIfPostExists(comment.getPostId());
    if(user.getStatus() != UserStatusDto.REGISTERED && user.getStatus() != UserStatusDto.ADMIN) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new comment");
    }
  }

  private void checkIfPostExists(long postId) throws PostNotFoundException {
    postFacade.getPost(postId);
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
