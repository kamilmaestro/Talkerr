package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.comment.domain.CommentFacade;
import com.kamilmarnik.talkerr.comment.dto.CommentDto;
import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto;
import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException;
import com.kamilmarnik.talkerr.post.dto.CreatePostDto;
import com.kamilmarnik.talkerr.post.dto.PostDto;
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException;
import com.kamilmarnik.talkerr.user.domain.UserFacade;
import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostFacade {

  PostRepository postRepository;
  UserFacade userFacade;
  CommentFacade commentFacade;

  public PostDto addPost(CreatePostDto post) throws UserRoleException {
    final UserDto user = userFacade.getLoggedUser();
    checkIfUserCanAddPost(user);

    return postRepository.save(Post.fromDto(createPost(post, user))).dto();
  }

  public PostDto getPost(long postId) throws PostNotFoundException {
    return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException("Post with Id: " + postId + " was not found"))
        .dto();
  }

  public void deletePost(long postId) throws PostNotFoundException {
    final UserDto user = userFacade.getLoggedUser();

    if (canUserDeletePost(postId, user)) {
      postRepository.deleteById(postId);
      commentFacade.deleteCommentsByPostId(postId);
    }
  }

  public List<PostDto> getPostsByTopicId(long topicId) {
    return postRepository.findAllByTopicId(topicId).stream()
        .map(Post::dto)
        .collect(Collectors.toList());
  }

  public CommentDto addCommentToPost(CreateCommentDto comment) throws UserRoleException, PostNotFoundException, InvalidCommentContentException {
    Objects.requireNonNull(comment, "Comment can not be created due to invalid data");
    getPost(comment.getPostId());

    return commentFacade.addComment(comment);
  }

  public void deletePostsByTopicId(long topicId) {
    final Set<Long> postsIds = postRepository.findPostsIdsByTopicId(topicId);
    postRepository.deletePostsByTopicId(topicId);
    commentFacade.deleteCommentsByPostIdIn(postsIds);
  }

  private void checkIfUserCanAddPost(UserDto user) throws UserRoleException {
    if (!userFacade.isAdminOrRegistered(user)) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new post");
    }
  }

  private boolean canUserDeletePost(long postId, UserDto user) throws PostNotFoundException {
    final PostDto post = getPost(postId);
    return post.getAuthorId() == user.getUserId() || userFacade.isAdmin(user);
  }

  private PostDto createPost(CreatePostDto post, UserDto author) {
    return PostDto.builder()
        .content(post.getContent())
        .createdOn(LocalDateTime.now())
        .authorId(author.getUserId())
        .topicId(post.getTopicId())
        .authorLogin(author.getLogin())
        .build();
  }
}
