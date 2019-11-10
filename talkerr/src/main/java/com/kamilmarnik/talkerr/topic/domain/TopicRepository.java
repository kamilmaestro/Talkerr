package com.kamilmarnik.talkerr.topic.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface TopicRepository extends JpaRepository<Topic, Long> {
  Optional<Topic> findByName(String name);
}
