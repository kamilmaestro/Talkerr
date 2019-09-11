package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.PostDto

import java.time.LocalDateTime

abstract class PostCreator {
    static PostDto createNewPost(long userId) {
        return createNewPost(userId, "DEFAULT CONTENT")
    }

    static PostDto createNewPost(long userId, String content) {
        return createNewPost(userId, content, LocalDateTime.now())
    }

    static PostDto createNewPost(long userId, String content, LocalDateTime date) {
        return PostDto.builder()
                .content(content)
                .createdOn(date)
                .userId(userId)
                .build()
    }
}
