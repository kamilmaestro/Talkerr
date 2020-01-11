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
