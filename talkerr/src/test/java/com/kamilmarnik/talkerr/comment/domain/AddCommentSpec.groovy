package com.kamilmarnik.talkerr.comment.domain

import com.kamilmarnik.talkerr.comment.dto.CommentDto
import com.kamilmarnik.talkerr.comment.exception.InvalidCommentContentException
import org.apache.commons.text.RandomStringGenerator

import static com.kamilmarnik.talkerr.comment.domain.CommentCreator.createNewComment
import static com.kamilmarnik.talkerr.user.domain.UserCreator.DEF_LOGIN
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class AddCommentSpec extends CommentSpec {

    def setup() {
        userFacade.registerUser(registerNewUser())
        loggedUserGetter.loggedUserName >> DEF_LOGIN
    }
    private static tooLongContent = new RandomStringGenerator.Builder().build().generate(CommentDto.MAX_CONTENT_LENGTH)

    def "user should be able to create a new comment" () {
        when: "user wants to add a comment"
            def comment = commentFacade.addComment(createNewComment(fstPostId))
        then: "comment is added"
            def savedComment = commentFacade.getComment(comment.commentId)
            savedComment.commentId == comment.commentId
        and: "it is created by logged in user"
            savedComment.authorId == comment.authorId
        and: "login of author is proper"
            savedComment.authorLogin == comment.authorLogin
    }

    def "user should not be able to create a new comment with wrong content" () {
        when: "user wants to add a comment"
            commentFacade.addComment(createNewComment(content, fstPostId))
        then: "comment is not added due to invalid content"
            thrown(expected)
        where:
            content         |   expected
            ""              |   InvalidCommentContentException.class
            "   "           |   InvalidCommentContentException.class
            tooLongContent  |   InvalidCommentContentException.class
            null            |   InvalidCommentContentException.class
    }
}
