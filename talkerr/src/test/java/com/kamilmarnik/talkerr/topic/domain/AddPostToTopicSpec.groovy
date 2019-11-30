package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException

import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.post.domain.PostCreator.getPost
import static com.kamilmarnik.talkerr.topic.domain.TopicCreator.createNewTopic
import static com.kamilmarnik.talkerr.user.domain.UserCreator.*

class AddPostToTopicSpec extends TopicSpec {

    def setup() {
        def admin = createAdmin(userRepository)
        def user = userFacade.registerUser(registerNewUser())
    }

    def "user should be able to create a new post"() {
        given: "there is a topic created by admin and other user is logged in"
            loggedUserGetter.loggedUserName >>> ["Admin", DEF_LOGIN]
            def topic = topicFacade.addTopic(createNewTopic())
        when: "this user creates a new post in an existing topic"
            postFacade.addPost(_) >> getPost(userId, fstPostId, topic.topicId)
            def post = topicFacade.addPostToTopic(createNewPost("FIRST POST", topic.getTopicId()))
        then: "post is created"
            post.postId == fstPostId
    }

    def "user should not be able to create a new post if there is no topic"() {
        given: "there is a logged in user"
            loggedUserGetter.loggedUserName >> DEF_LOGIN
        when: "user wants to add a post but there is no topic"
            def post = topicFacade.addPostToTopic(createNewPost("FIRST POST", fstTopicId))
        then: "post is not created"
            thrown(TopicNotFoundException.class)
    }
}
