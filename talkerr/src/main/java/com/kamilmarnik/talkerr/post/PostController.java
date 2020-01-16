package com.kamilmarnik.talkerr.post;

import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto;
import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException;
import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/post")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@Slf4j
class PostController {

  PostFacade postFacade;

  @Autowired
  PostController(@Autowired PostFacade postFacade) {
    this.postFacade = postFacade;
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostDto> getPost(@PathVariable Long postId) throws PostNotFoundException {
      PostDto post = postFacade.getPost(postId);

      return ResponseEntity.ok(post);
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deletePost(@PathVariable Long postId) throws PostNotFoundException {
      postFacade.deletePost(postId);

      return ResponseEntity.ok().build();
  }

  @GetMapping("/topic/{topicId}")
  public ResponseEntity<List<PostDto>> getPostsByTopicId(@PathVariable long topicId) {
    List<PostDto> posts = postFacade.getPostsByTopicId(topicId);

    return ResponseEntity.ok(posts);
  }

  @PostMapping("/comment/")
  public ResponseEntity<CommentDto> addCommentToPost(@RequestBody CreateCommentDto comment) throws PostNotFoundException, UserRoleException, InvalidCommentContentException {
    CommentDto savedComment = postFacade.addCommentToPost(comment);

    return ResponseEntity.ok(savedComment);
  }
}
