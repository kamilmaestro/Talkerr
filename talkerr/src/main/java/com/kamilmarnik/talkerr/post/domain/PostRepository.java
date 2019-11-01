package com.kamilmarnik.talkerr.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<Post, Long> {
  Page<Post> findAllByTopicId(Pageable pageable, long topicId);
}
