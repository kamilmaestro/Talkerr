package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.topic.exception.InvalidTopicContentException
import com.kamilmarnik.talkerr.user.exception.UserRoleException

import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin
import static com.kamilmarnik.talkerr.user.domain.UserCreator.createGuest
import static com.kamilmarnik.talkerr.user.domain.UserCreator.getDEF_LOGIN
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser
import static com.kamilmarnik.talkerr.topic.domain.TopicCreator.createNewTopic

class AddTopicSpec extends TopicSpec {

    def setup() {
        def admin = createAdmin(userRepository)
        def user = userFacade.registerUser(registerNewUser())
    }

    def "admin should be able to add a new topic" () {
        given: "there is an admin"
            loggedUserGetter.getLoggedUserName() >> "Admin"
        when: "admin creates a new topic"
            def topic = topicFacade.addTopic(createNewTopic("First topic", "Description"))
        then: "the topic is created"
            topic.topicId == topicFacade.getTopic(topic.topicId).topicId
    }

    def "registered user without a permission should not be able to add a new topic" () {
        given: "there is an user"
            loggedUserGetter.getLoggedUserName() >> DEF_LOGIN
        when: "user wants to add a topic"
            topicFacade.addTopic(createNewTopic("Topic", "Description"))
        then: "he is not able to do this"
            thrown(UserRoleException.class)
    }

    def "guest can not add a new topic" () {
        given: "there is a guest"
            def guest = createGuest(userRepository)
            loggedUserGetter.getLoggedUserName() >> "Guest"
        when: "guest wants to add a new topic"
            topicFacade.addTopic(createNewTopic("Topic", "Description"))
        then: "he is not able to do this"
            thrown(UserRoleException.class)
    }

    def "topic can not be created with wrong content" () {
        given: "there is an admin"
            loggedUserGetter.getLoggedUserName() >> "Admin"
        when: "admin wants to add topic"
            topicFacade.addTopic(createNewTopic(name, description))
        then: "exception #excpected is thrown instead of creating a new topic"
            thrown(expected)
        where:
            name        |   description     |   expected
            longName    |   "Description"   |   InvalidTopicContentException.class
            "Name"      |   longDescription |   InvalidTopicContentException.class
            longName    |   longDescription |   InvalidTopicContentException.class
    }

    private static String longName = "Definitely too long!!!!!01234567899876543210"
    private static String longDescription = "Definitely too long description!!!!!01234567899876543210012345678998765432100123456789987654321001234567899876543210"
}
