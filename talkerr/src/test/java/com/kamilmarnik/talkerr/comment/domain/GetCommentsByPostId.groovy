package com.kamilmarnik.talkerr.comment.domain


import static com.kamilmarnik.talkerr.comment.domain.CommentCreator.createNewComment
import static com.kamilmarnik.talkerr.user.domain.UserCreator.getDEF_LOGIN
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class GetCommentsByPostId extends CommentSpec {

    def setup() {
        def user = userFacade.registerUser(registerNewUser())
        loggedUserGetter.loggedUserName >> DEF_LOGIN
    }

    def "user should be able to get list of comments of one post" () {
        given: "there are three comments written on post"
            def fstComment = commentFacade.addComment(createNewComment("First", fstPostId))
            def sndComment = commentFacade.addComment(createNewComment("Second", fstPostId))
            def trdComment = commentFacade.addComment(createNewComment("Third", fstPostId))
        when: "user wants to get those comments"
            def comments = commentFacade.getCommentsByPostId(fstPostId)
        then: "user gets list of two comments"
            comments.size() == 3
        and: "they are connected with post"
            comments.stream().filter({ c -> c.postId == fstPostId }).count() == 3
    }
}
