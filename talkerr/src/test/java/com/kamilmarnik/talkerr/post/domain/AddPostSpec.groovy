package com.kamilmarnik.talkerr.post.domain


import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.topic.domain.TopicCreator.createNewTopic
import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class AddPostSpec extends PostSpec {

    def "user should be able to create a new post"() {
        given: "there is a topic created by admin"
            def admin = createAdmin(userRepository)
            topicFacade.addTopic(createNewTopic()) >> getTopic(admin.getUserId())
            topicFacade.getTopic(topicId) >> getTopic(admin.getUserId())
        and: "user is logged"
            loggedUserGetter.loggedUserName >> "DefLog"
            def user = userFacade.registerUser(registerNewUser())
        when: "user creates a new post in an existing topic"
            def post = postFacade.addPost(createNewPost("FIRST POST", topicId))
        then: "post is created"
            def createdPost = postFacade.getPost(post.postId)
        and: "post was created correctly"
            createdPost.postId == post.postId
        and: "post was created by logged user"
            createdPost.authorId == user.userId

    }
}
