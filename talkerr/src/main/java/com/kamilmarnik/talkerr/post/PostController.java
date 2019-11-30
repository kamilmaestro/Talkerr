package com.kamilmarnik.talkerr.post;

import com.kamilmarnik.talkerr.post.domain.PostFacade;
import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//  @PostMapping("/")
//  public ResponseEntity<PostDto> addPost(@RequestBody CreatePostDto post) throws UserRoleException, TopicNotFoundException {
//      PostDto addedPost = postFacade.addPost(post);
//
//      return ResponseEntity.ok(addedPost);
//  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deletePost(@PathVariable Long postId) throws PostNotFoundException {
      postFacade.deletePost(postId);

      return ResponseEntity.ok().build();
  }

  @GetMapping("/topic/{topicId}")
  public ResponseEntity<Page<PostDto>> getPostsByTopicId(@PathVariable long topicId, Pageable pageable) throws TopicNotFoundException {
    Page<PostDto> posts = postFacade.getPostsByTopicId(pageable, topicId);

    return ResponseEntity.ok(posts);
  }
}
