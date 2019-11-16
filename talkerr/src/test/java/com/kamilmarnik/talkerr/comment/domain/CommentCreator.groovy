package com.kamilmarnik.talkerr.comment.domain


import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto

abstract class CommentCreator {
    static createNewComment(long postId) {
        createNewComment("Default content", postId)
    }

    static createNewComment(String content, long postId) {
        CreateCommentDto.builder()
            .content(content)
            .postId(postId)
            .build()
    }
}
