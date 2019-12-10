package com.kamilmarnik.talkerr.comment.domain

import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException

import static com.kamilmarnik.talkerr.user.domain.UserCreator.getDEF_LOGIN
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class DeleteCommentsByPostIdSpec extends CommentSpec {

    def setup() {
        def user = userFacade.registerUser(registerNewUser())
        loggedUserGetter.loggedUserName >> DEF_LOGIN
    }

    def "user should be able to delete post with its comments" () {
        given: "there is a comment"
            def create = CommentCreator.createNewComment(sndPostId)
            def fstComment = commentFacade.addComment(create)
        when: "user deletes a post and wants to delete its comment as well"
            commentFacade.deleteCommentsByPostId(sndPostId)
        and: "he checks if comment was deleted"
            commentFacade.getComment(fstComment.commentId)
        then: "comment is deleted"
            thrown(CommentNotFoundException.class)
    }
}
