package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.CreatePostDto

abstract class PostCreator {
    static CreatePostDto createNewPost(long topicId) {
        return createNewPost("DEFAULT CONTENT", topicId)
    }

    static CreatePostDto createNewPost(String content, long topicId) {
        return CreatePostDto.builder()
                .content(content)
                .topicId(topicId)
                .build()
    }
}
