package com.kamilmarnik.talkerr.comment.domain

import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException

import static com.kamilmarnik.talkerr.comment.domain.CommentCreator.createNewComment
import static com.kamilmarnik.talkerr.user.domain.UserCreator.DEF_LOGIN
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class AddCommentSpec extends CommentSpec {

    def setup() {
        def user = userFacade.registerUser(registerNewUser())
        loggedUserGetter.loggedUserName >> DEF_LOGIN
    }

    def "user should be able to create a new comment" () {
        when: "user wants to add a comment"
            def comment = commentFacade.addComment(createNewComment(fstPostId))
        then: "comment is added"
            def savedComment = commentFacade.getComment(comment.commentId)
            savedComment.commentId == comment.commentId
        and: "it is created by logged in user"
            savedComment.authorId == comment.authorId
    }

    def "user should not be able to create a new comment with wrong content" () {
        when: "user wants to add a comment"
            def comment = commentFacade.addComment(createNewComment(content, fstPostId))
        then: "comment is not added due to invalid data"
            thrown(expected)
        where:
            content |   expected
            ""      |   InvalidCommentContentException.class
            null    |   InvalidCommentContentException.class
            "   "   |   InvalidCommentContentException.class
    }
}
