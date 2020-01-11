package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.topic.dto.TopicDto
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicDescriptionException
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicNameException
import com.kamilmarnik.talkerr.user.exception.UserRoleException
import org.apache.commons.text.RandomStringGenerator

import static com.kamilmarnik.talkerr.topic.domain.TopicCreator.createNewTopic
import static com.kamilmarnik.talkerr.user.domain.UserCreator.*

class AddTopicSpec extends TopicSpec {

    def setup() {
        createAdmin(userRepository)
        userFacade.registerUser(registerNewUser())
    }
    private static String tooLongName = new RandomStringGenerator.Builder().build().generate(TopicDto.MAX_NAME_LENGTH + 1)
    private static String tooLongDescription = new RandomStringGenerator.Builder().build().generate(TopicDto.MAX_DESCRIPTION_LENGTH + 1)

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
            name        |   description        |   expected
            tooLongName |   "Description"      |   InvalidTopicNameException.class
            "Name"      |   tooLongDescription |   InvalidTopicDescriptionException.class
            tooLongName |   tooLongDescription |   InvalidTopicNameException.class
            null        |   "Description"      |   InvalidTopicNameException.class
            "Name"      |   null               |   InvalidTopicDescriptionException.class
            null        |   null               |   InvalidTopicNameException.class
    }
}
