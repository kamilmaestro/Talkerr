package com.kamilmarnik.talkerr.post.domain

import static com.kamilmarnik.talkerr.user.domain.UserCreator.DEF_LOGIN
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser
import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost

class AddPostSpec extends PostSpec {

    def setup() {
        def user = userFacade.registerUser(registerNewUser())
        loggedUserGetter.loggedUserName >> DEF_LOGIN
    }

    def "registered user should be able to add a new post" () {
        when: "user wants to add a new post"
            def post = postFacade.addPost(createNewPost("First post", fstTopicId))
        then: "post is created"
            def savedPost = postFacade.getPost(post.postId)
            savedPost.postId == post.postId
        and: "content is proper"
            savedPost.content == post.content
    }
}
