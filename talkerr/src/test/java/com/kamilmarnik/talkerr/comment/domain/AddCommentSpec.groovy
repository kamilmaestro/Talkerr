package com.kamilmarnik.talkerr.comment.domain

import com.kamilmarnik.talkerr.post.domain.PostCreator
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException

import static com.kamilmarnik.talkerr.comment.domain.CommentCreator.createNewComment
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class AddCommentSpec extends CommentSpec {

    def setup() {
        loggedUserGetter.loggedUserName >> "DefLog"
    }
    def user = userFacade.registerUser(registerNewUser())

    def "user should be able add a comment to an existing post" () {
        when: "user wants to add a comment to this post"
            def comment = commentFacade.addComment(createNewComment(fstPostId))
        then: "comment is created"
            comment.commentId == commentFacade.getComment(comment.commentId).commentId
    }

    def "user should not be able to add a comment if post does not exist" () {
        given: "there is a post"
            postFacade.getPost(fstPostId) >> {throw new PostNotFoundException()}
        when: "user wants to add a comment to post which does not exists"
            def comment = commentFacade.addComment(createNewComment(fstPostId))
        then: "post was not found and user can not add a comment"
            thrown PostNotFoundException.class
    }

    def "user can not add a comment to other post which does not exist" () {
        given: "there is a post"
            postFacade.addPost(PostCreator.createNewPost(topicId)) >> getPost(user.getUserId(), fstPostId)
            postFacade.getPost(fstPostId) >> getPost(user.getUserId(), fstPostId)
        and: "there is a post which has not been created properly and it does not exist"
            postFacade.getPost(sndPostId) >> {throw new PostNotFoundException()}
        when: "user wants to add a comment to other post which does not exist"
            def comment = commentFacade.addComment(createNewComment(sndPostId))
        then: "second post was not found and user can not add a comment"
            thrown PostNotFoundException.class
    }

}
