package com.kamilmarnik.talkerr.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByTopicId(long topicId);

  @Query("SELECT p.postId FROM com.kamilmarnik.talkerr.post.domain.Post p WHERE topic_id = :topicId")
  Set<Long> findPostsIdsByTopicId(@Param("topicId") long topicId);

  @Transactional
  void deletePostsByTopicId(long topicId);
}
