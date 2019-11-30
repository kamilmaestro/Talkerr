package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.CreatePostDto
import com.kamilmarnik.talkerr.post.dto.PostDto

import java.time.LocalDateTime

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

    static PostDto getPost(long authorId, long postId, long topicId) {
        PostDto.builder()
                .postId(postId)
                .content("Post")
                .createdOn(LocalDateTime.now())
                .authorId(authorId)
                .topicId(topicId)
                .build()
    }
}
