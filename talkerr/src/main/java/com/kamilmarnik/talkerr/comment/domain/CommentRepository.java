package com.kamilmarnik.talkerr.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

interface CommentRepository extends JpaRepository<Comment, Long> {
  @Transactional
  void deleteCommentsByPostId(long postId);
}
