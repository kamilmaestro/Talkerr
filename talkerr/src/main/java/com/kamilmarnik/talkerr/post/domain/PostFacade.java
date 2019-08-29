package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostAlreadyExists;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserRepository;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostFacade {

  PostRepository postRepository;
  UserRepository userRepository;

  public PostDto addPost(PostDto post) throws PostAlreadyExists, UserNotFoundException {
    postRepository.findById(post.getPostId())
      .orElseThrow(() -> new PostAlreadyExists("Can not add post with id: " + post.getPostId()));

    userRepository.findById(post.getPostId())
      .orElseThrow(() -> new UserNotFoundException("User not found with id: " + post.getUserId()));

    return postRepository.save(Post.fromDto(post)).dto();
  }

  public PostDto getPost(long postId) throws PostNotFoundException {
    return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post with postId: " + postId + "was not found"))
        .dto();
  }
}
