package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.CreatedPostDto;
import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import com.kamilmarnik.talkerr.user.exception.UserNotFoundException;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostFacade {

  PostRepository postRepository;
  UserFacade userFacade;

  public PostDto addPost(CreatedPostDto post) throws UserNotFoundException, UserRoleException {
    Objects.requireNonNull(post);
    UserStatusDto userStatus = userFacade.getUser(post.getUserId()).getStatus();
    if(!UserStatusDto.ADMIN.equals(userStatus) && !UserStatusDto.REGISTERED.equals(userStatus)) {
      throw new UserRoleException("User with Id:" + post.getUserId() + " does not have a permission to add a new post");
    }

    return postRepository.save(Post.fromDto(createPost(post))).dto();
  }

  public PostDto getPost(long postId) throws PostNotFoundException {
    return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post with Id: " + postId + " was not found"))
        .dto();
  }

  public void deletePost(long postId, long loggedUserId) throws UserNotFoundException, PostNotFoundException {
    UserDto user = userFacade.getUser(loggedUserId);
    PostDto post = getPost(postId);

    if(post.getUserId() == loggedUserId || user.getStatus().equals(UserStatusDto.ADMIN)) {
      postRepository.deleteById(postId);
    }
  }

  private PostDto createPost(CreatedPostDto post) {
    return PostDto.builder()
        .content(post.getContent())
        .userId(post.getUserId())
        .createdOn(LocalDateTime.now())
        .build();
  }
}
