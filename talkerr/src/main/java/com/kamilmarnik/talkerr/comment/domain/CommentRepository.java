package com.kamilmarnik.talkerr.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

interface CommentRepository extends JpaRepository<Comment, Long> {
  @Transactional
  void deleteCommentsByPostId(long postId);

  @Transactional
  void deleteCommentsByPostIdIn(Set<Long> postsIds);

  List<Comment> findCommentsByPostIdOrderByCreatedOn(long postId);
}
