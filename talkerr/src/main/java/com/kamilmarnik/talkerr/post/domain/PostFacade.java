package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostAlreadyExists;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserRepository;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostFacade {

  PostRepository postRepository;
  UserRepository userRepository;

  public PostDto addPost(PostDto post) throws Exception {
    UserDto user = userRepository.findById(post.getUserId())
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + post.getUserId()))
        .dto();

    Optional<Post> savedPost = postRepository.findById(post.getPostId());
    if(savedPost.isPresent()) {
      throw new PostAlreadyExists("There is a post with id: " + post.getPostId());
    }

    return postRepository.save(Post.fromDto(post)).dto();
  }


  public PostDto getPost(long postId) throws PostNotFoundException {
    return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post with postId: " + postId + "was not found"))
        .dto();
  }
}
