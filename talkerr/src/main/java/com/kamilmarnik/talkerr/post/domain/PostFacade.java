package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostFacade {

  PostRepository postRepository;
  UserFacade userFacade;

  public PostDto addPost(PostDto post) throws UserNotFoundException, UserRoleException {
    Objects.requireNonNull(post);
    UserStatusDto userStatus = userFacade.getUser(post.getUserId()).getStatus();
    if(userStatus != UserStatusDto.ADMIN && userStatus != UserStatusDto.REGISTERED) {
      throw new UserRoleException("User with Id:" + post.getUserId() + " does not have a permission to add a new post");
    }

    return postRepository.save(Post.fromDto(post)).dto();
  }

  public PostDto getPost(long postId) throws PostNotFoundException {
    return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post with Id: " + postId + "was not found"))
        .dto();
  }
}
