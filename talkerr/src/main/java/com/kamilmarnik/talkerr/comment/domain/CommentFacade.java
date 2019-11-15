package com.kamilmarnik.talkerr.comment.domain;

import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto;
import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException;
import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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

  public CommentDto addComment(CreateCommentDto comment) {
    UserDto loggedUser = userFacade.getLoggedUser();
    return commentRepository.save(Comment.fromDto(createComment(comment, loggedUser.getUserId()))).dto();
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
