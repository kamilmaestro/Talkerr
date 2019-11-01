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
    UserDto user = userFacade.getLoggedUser();
    checkIfUserCanAddPost(user, post);

    return postRepository.save(Post.fromDto(createPost(post, user.getUserId()))).dto();
  }

  public PostDto getPost(long postId) throws PostNotFoundException {
    return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post with Id: " + postId + " was not found"))
        .dto();
  }

  public void deletePost(long postId) throws PostNotFoundException {
    UserDto user = userFacade.getLoggedUser();
    PostDto post = getPost(postId);

    if(post.getAuthorId() == user.getUserId() || user.getStatus().equals(UserStatusDto.ADMIN)) {
      postRepository.deleteById(postId);
    }
  }

  private void checkIfUserCanAddPost(UserDto user, CreatedPostDto post) throws UserRoleException {
    Objects.requireNonNull(post, "Post can not be created due to invalid data");
    if((!userFacade.isAdmin(user) && !userFacade.isRegistered(user))) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new post");
    }
  }

  private PostDto createPost(CreatedPostDto post, long authorId) {
    return PostDto.builder()
        .content(post.getContent())
        .authorId(authorId)
        .createdOn(LocalDateTime.now())
        .build();
  }
}
