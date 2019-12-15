package com.kamilmarnik.talkerr.comment.domain

import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException
import com.kamilmarnik.talkerr.user.domain.UserCreator

class DeleteCommentSpec extends CommentSpec {

    def setup() {
        def fstUser = userFacade.registerUser(UserCreator.registerNewUser("First"))
        def sndUser = userFacade.registerUser(UserCreator.registerNewUser("Second"))
    }

    def "user should be able to delete his own comment" () {
        given: "there is user logged in"
            loggedUserGetter.loggedUserName >> "First"
        and: "comment was added by him"
            def comment = commentFacade.addComment(CommentCreator.createNewComment(fstPostId))
        when: "user wants to delete this comment"
            commentFacade.deleteComment(comment.commentId)
        and: "checks if comment was deleted"
            commentFacade.getComment(comment.commentId)
        then: "comment is deleted"
            thrown CommentNotFoundException.class
    }

    def "user should not be able to delete a comment written by other user" () {
        given: "there is a comment written by other user"
            loggedUserGetter.loggedUserName >>> ["First", "Second"]
            def comment = commentFacade.addComment(CommentCreator.createNewComment(fstPostId))
        and: "second user is logged in and he wants to delete it"
            commentFacade.deleteComment(comment.commentId)
        when: "second user wants to check if comment was deleted"
            def savedComment = commentFacade.getComment(comment.commentId)
        then: "comment was not deleted"
            savedComment.commentId == comment.commentId
    }
}
