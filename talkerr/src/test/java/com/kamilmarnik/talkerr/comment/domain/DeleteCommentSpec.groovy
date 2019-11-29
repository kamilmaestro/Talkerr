package com.kamilmarnik.talkerr.comment.domain


import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException
import com.kamilmarnik.talkerr.post.domain.PostCreator
import com.kamilmarnik.talkerr.user.domain.UserCreator

class DeleteCommentSpec extends CommentSpec {

    def setup() {
        postFacade.addPost(PostCreator.createNewPost(topicId)) >> getPost(fstUser.userId, fstPostId)
    }
    def fstUser = userFacade.registerUser(UserCreator.registerNewUser("First"))
    def sndUser = userFacade.registerUser(UserCreator.registerNewUser("Second"))

    def "user should be able to delete his own comment" () {
        given: "there is a comment"
            loggedUserGetter.loggedUserName >> "First"
            def comment = commentFacade.addComment(CommentCreator.createNewComment(fstPostId))
        and: "it was deleted by user"
            commentFacade.deleteComment(comment.commentId)
        when: "user wants to check if comment was deleted"
            commentFacade.getComment(comment.commentId)
        then: "comment is deleted"
            thrown CommentNotFoundException.class
    }

    def "user should be able to delete a comment written by other user" () {
        given: "there is a comment written by other user"
            loggedUserGetter.loggedUserName >>> ["First", "Second"]
            def comment = commentFacade.addComment(CommentCreator.createNewComment(fstPostId))
        and: "second user is logged in and he wants to delete it"
            commentFacade.deleteComment(comment.commentId)
        when: "user wants to check if comment was deleted"
            def savedComment = commentFacade.getComment(comment.commentId)
        then: "comment was not deleted"
            savedComment.commentId == comment.commentId
    }
}
