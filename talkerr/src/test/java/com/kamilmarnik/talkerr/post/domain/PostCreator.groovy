package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.CreatedPostDto

abstract class PostCreator {
    static CreatedPostDto createNewPost(long topicId) {
        return createNewPost("DEFAULT CONTENT", topicId)
    }

    static CreatedPostDto createNewPost(String content, long topicId) {
        return CreatedPostDto.builder()
                .content(content)
                .topicId(topicId)
                .build()
    }
}
