package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.CreatedPostDto

abstract class PostCreator {
    static CreatedPostDto createNewPost(long userId) {
        return createNewPost(userId, "DEFAULT CONTENT")
    }

    static CreatedPostDto createNewPost(long userId, String content) {
        return CreatedPostDto.builder()
                .content(content)
                .creatorId(userId)
                .build()
    }
}
