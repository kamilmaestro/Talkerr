package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.PostDto

import java.time.LocalDateTime

abstract class PostCreator {
    static PostDto createNewPost(long userId) {
        return createNewPost(userId, "DEFAULT CONTENT")
    }

    static PostDto createNewPost(long userId, String content) {
        return createNewPost(userId, content, LocalDateTime.now().toDate())
    }

    static PostDto createNewPost(long userId, String content, Date date) {
        return PostDto.builder()
                .content(content)
                .date(date)
                .userId(userId)
                .build()
    }
}
