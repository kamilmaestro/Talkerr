package com.kamilmarnik.talkerr.post.domain

import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser
import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.user.domain.UserCreator.DEF_LOGIN

class DeletePostsByTopicIdSpec extends PostSpec {

    def setup() {
        def user = userFacade.registerUser(registerNewUser())
        loggedUserGetter.loggedUserName >> DEF_LOGIN
    }

    def "user should be able to delete post by topicId" () {
        given: "there are two posts written for this topic"
            postFacade.addPost(createNewPost("First", fstTopicId))
            postFacade.addPost(createNewPost("Second", fstTopicId))
        when: "user deletes topic and wants to delete its posts"
            postFacade.deletePostsByTopicId(fstTopicId)
        then: "post are deleted as well"
            postFacade.getPostsByTopicId(fstTopicId).isEmpty()
    }
}
