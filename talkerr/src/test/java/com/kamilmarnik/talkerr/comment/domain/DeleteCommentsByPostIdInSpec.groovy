package com.kamilmarnik.talkerr.comment.domain

import com.kamilmarnik.talkerr.comment.exception.CommentNotFoundException

import static com.kamilmarnik.talkerr.user.domain.UserCreator.getDEF_LOGIN
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser
import static com.kamilmarnik.talkerr.comment.domain.CommentCreator.createNewComment

class DeleteCommentsByPostIdInSpec extends CommentSpec {

    def setup() {
        def user = userFacade.registerUser(registerNewUser())
        loggedUserGetter.loggedUserName >> DEF_LOGIN
    }

    def "comments should be deleted after deleting a few posts connected with them" () {
        given: "there are three comments in two posts"
            def fstComment = commentFacade.addComment(createNewComment("First post", fstPostId))
            def sndComment = commentFacade.addComment(createNewComment("First post", fstPostId))
            def trdComment = commentFacade.addComment(createNewComment("Second post", sndPostId))
        when: "user wants to deletes comments by list of their posts ids"
            commentFacade.deleteCommentsByPostIdIn([fstPostId, sndPostId].toSet())
        and: "wants to check if they were deleted"
            commentFacade.getComment(fstComment.commentId)
            commentFacade.getComment(fstComment.commentId)
            commentFacade.getComment(fstComment.commentId)
        then: "comments are deleted"
            thrown(CommentNotFoundException.class)
    }
}
