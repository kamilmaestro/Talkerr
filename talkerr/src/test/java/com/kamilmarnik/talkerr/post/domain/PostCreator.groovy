package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.CreatedPostDto

abstract class PostCreator {
    static CreatedPostDto createNewPost() {
        return createNewPost("DEFAULT CONTENT")
    }

    static CreatedPostDto createNewPost(String content) {
        return CreatedPostDto.builder()
                .content(content)
                .build()
    }
}
