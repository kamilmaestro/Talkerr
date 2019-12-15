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
import com.kamilmarnik.talkerr.user.dto.UserStatusDto;
import com.kamilmarnik.talkerr.user.exception.UserRoleException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostFacade {

  PostRepository postRepository;
  UserFacade userFacade;
  CommentFacade commentFacade;

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

    if(canUserDeletePost(post, user)) {
      postRepository.deleteById(postId);
      commentFacade.deleteCommentsByPostId(postId);
    }
  }

  public Page<PostDto> getPostsByTopicId(Pageable pageable, long topicId) {
    Objects.requireNonNull(pageable, "Wrong page or size of list of posts");

    return postRepository.findAllByTopicId(pageable, topicId).map(Post::dto);
  }

  public CommentDto addCommentToPost(CreateCommentDto comment) throws UserRoleException, PostNotFoundException, InvalidCommentContentException {
    Objects.requireNonNull(comment, "Comment can not be created due to invalid data");
    getPost(comment.getPostId());

    return commentFacade.addComment(comment);
  }

  public void deletePostsByTopicId(long topicId) {
    Set<Long> postsIds = postRepository.findPostsIdsByTopicId(topicId);
    postRepository.deletePostsByTopicId(topicId);
    commentFacade.deleteCommentsByPostIdIn(postsIds);
  }

  private void checkIfUserCanAddPost(UserDto user, CreatePostDto post) throws UserRoleException {
    if(!userFacade.isAdminOrRegistered(user)) {
      throw new UserRoleException("User with username: " + user.getLogin() + " does not have a permission to add a new post");
    }
  }

  private boolean canUserDeletePost(PostDto post, UserDto user) {
    return post.getAuthorId() == user.getUserId() || user.getStatus().equals(UserStatusDto.ADMIN);
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
