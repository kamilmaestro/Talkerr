package com.kamilmarnik.talkerr.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<Post, Long> {
}
