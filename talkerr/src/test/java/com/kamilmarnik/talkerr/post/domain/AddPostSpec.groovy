package com.kamilmarnik.talkerr.post.domain


import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class AddPostSpec extends PostSpec {

    def "user should be able to create a new post"() {
        given: "there is an user"
            loggedUserGetter.loggedUserName >> "DefLog"
            def user = userFacade.registerUser(registerNewUser())
        when: "user creates a new post"
            def post = postFacade.addPost(createNewPost(user.userId, "FIRST POST"))
            def sndPost = postFacade.addPost(createNewPost(user.userId, "SECOND POST"))
        then: "post is created"
            def createdPost = postFacade.getPost(post.postId)
        and: "post was created correctly"
            createdPost.postId == post.postId
        and: "post was created by user"
            createdPost.userId == user.userId

    }
}
