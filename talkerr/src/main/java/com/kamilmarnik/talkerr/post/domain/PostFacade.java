package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.CreatePostDto;
import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostFacade {

  PostRepository postRepository;
  UserFacade userFacade;

  public PostDto addPost(CreatePostDto post) throws UserRoleException {
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

  public Page<PostDto> getPostsByTopicId(Pageable pageable, long topicId) {
    Objects.requireNonNull(pageable, "Wrong page or size of list of posts");

    return postRepository.findAllByTopicId(pageable, topicId).map(Post::dto);
  }

  private void checkIfUserCanAddPost(UserDto user, CreatePostDto post) throws UserRoleException {
    Objects.requireNonNull(post, "Post can not be created due to invalid data");
    if(!userFacade.isAdminOrRegistered(user)) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new post");
    }
  }

  private PostDto createPost(CreatePostDto post, long authorId) {
    return PostDto.builder()
        .content(post.getContent())
        .createdOn(LocalDateTime.now())
        .authorId(authorId)
        .topicId(post.getTopicId())
        .build();
  }
}
