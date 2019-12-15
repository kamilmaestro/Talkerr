package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.exception.PostNotFoundException

import static com.kamilmarnik.talkerr.comment.domain.CommentCreator.createNewComment
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser
import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost

class AddCommentToPostSpec extends PostSpec{

    def setup() {
        loggedUserGetter.loggedUserName >> "DefLog"
    }
    def user = userFacade.registerUser(registerNewUser())

    def "user should be able add a comment to an existing post" () {
        given: "there is a post created by user"
            def post = postFacade.addPost(createNewPost(fstTopicId))
        when: "user wants to add a comment to this post"
            commentFacade.addComment(_) >> getComment(user.userId, post.postId, fstCommentId)
            def comment = postFacade.addCommentToPost(createNewComment(post.postId))
        then: "comment is created"
            comment.commentId == fstCommentId
    }

    def "user should not be able to add a comment if post does not exist" () {
        when: "user wants to add a comment to post which does not exists"
            def comment = postFacade.addCommentToPost(createNewComment(fstPostId))
        then: "post was not found and user can not add a comment"
            thrown(PostNotFoundException.class)
    }

    def "user can not add a comment to other post which does not exist" () {
        given: "there is a post"
            def post = postFacade.addPost(createNewPost(fstTopicId))
        when: "user wants to add a comment to other post which does not exist"
            def comment = postFacade.addCommentToPost(createNewComment(sndPostId))
        then: "second post was not found and user can not add a comment"
            thrown(PostNotFoundException.class)
    }

}
