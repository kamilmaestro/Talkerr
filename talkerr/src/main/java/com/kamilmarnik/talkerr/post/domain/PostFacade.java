package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.CreatedPostDto;
import com.kamilmarnik.talkerr.post.dto.PostDto;
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
public class PostFacade {

  PostRepository postRepository;
  UserFacade userFacade;

  public PostDto addPost(CreatedPostDto post) throws UserRoleException {
    Objects.requireNonNull(post);
    checkIfUserCanAddPost(post.getCreatorId());

    return postRepository.save(Post.fromDto(createPost(post))).dto();
  }

  public PostDto getPost(long postId) throws PostNotFoundException {
    return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post with Id: " + postId + " was not found"))
        .dto();
  }

  public void deletePost(long postId) throws PostNotFoundException {
    UserDto user = userFacade.getLoggedUser();
    PostDto post = getPost(postId);

    if(post.getUserId() == user.getUserId() || user.getStatus().equals(UserStatusDto.ADMIN)) {
      postRepository.deleteById(postId);
    }
  }

  private void checkIfUserCanAddPost(long creatorId) throws UserRoleException {
    UserDto user = userFacade.getLoggedUser();
    if(!isAdminOrRegistered(user.getStatus()) || !isPostCreator(user.getUserId(), creatorId)) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new post");
    }
  }

  private boolean isAdminOrRegistered(UserStatusDto loggedUserStatus) {
    return UserStatusDto.ADMIN.equals(loggedUserStatus) || UserStatusDto.REGISTERED.equals(loggedUserStatus);
  }

  private boolean isPostCreator(long loggedUserId, long creatorId) {
    return loggedUserId == creatorId;
  }

  private PostDto createPost(CreatedPostDto post) {
    return PostDto.builder()
        .content(post.getContent())
        .userId(post.getCreatorId())
        .createdOn(LocalDateTime.now())
        .build();
  }
}
