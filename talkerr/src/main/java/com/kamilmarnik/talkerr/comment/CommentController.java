package com.kamilmarnik.talkerr.comment;

import com.kamilmarnik.talkerr.comment.domain.CommentFacade;
import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
class CommentController {

  CommentFacade commentFacade;

  @Autowired
  public CommentController(@Autowired CommentFacade commentFacade) {
    this.commentFacade = commentFacade;
  }

  @GetMapping("/{commentId}")
  ResponseEntity<CommentDto> getComment(@PathVariable long commentId) throws CommentNotFoundException {
    CommentDto comment = commentFacade.getComment(commentId);

    return ResponseEntity.ok(comment);
  }

  @DeleteMapping("/{commentId}")
  ResponseEntity<?> deleteComment(@PathVariable long commentId) throws CommentNotFoundException {
    commentFacade.deleteComment(commentId);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/post/{postId}")
  ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId) {
    List<CommentDto> comments = commentFacade.getCommentsByPostId(postId);

    return ResponseEntity.ok(comments);
  }
}
