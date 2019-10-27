package com.kamilmarnik.talkerr.topic.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface TopicRepository extends JpaRepository<Topic, Long> {
}
