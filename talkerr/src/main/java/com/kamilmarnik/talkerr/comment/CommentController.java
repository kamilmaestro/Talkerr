package com.kamilmarnik.talkerr.comment;

import com.kamilmarnik.talkerr.comment.domain.CommentFacade;
import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/post/{postId}")
  ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId) {
    List<CommentDto> comments = commentFacade.getCommentsByPostId(postId);

    return ResponseEntity.ok(comments);
  }
}
