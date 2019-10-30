package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.topic.dto.CreateTopicDto
import com.kamilmarnik.talkerr.topic.exception.InvalidTopicContentException
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import com.kamilmarnik.talkerr.user.exception.UserRoleException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.util.stream.Collectors

import static com.kamilmarnik.talkerr.user.domain.UserCreator.*

class TopicSpec extends Specification{

    UserRepository userRepository = new InMemoryUserRepository()
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    TopicRepository topicRepository = new InMemoryTopicRepository()
    UserFacade userFacade = new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder,loggedUserGetter)
    TopicFacade topicFacade = new TopicFacadeCreator().createTopicFacade(topicRepository, userFacade)

    def admin = createAdmin(userRepository)
    def user = userFacade.registerUser(registerNewUser())

    def "admin should be able to add a new topic" () {
        given: "there is an admin"
            loggedUserGetter.getLoggedUserName() >> "Admin"
        when: "admin creates a new topic"
            def topic = topicFacade.addTopic(createTopic("First topic", "Description"))
        then: "the topic is created"
            topic.topicId == topicFacade.getTopic(topic.topicId).topicId
    }

    def "registered user without a permission should not be able to add a new topic" () {
        given: "there is an user"
            loggedUserGetter.getLoggedUserName() >> DEF_LOGIN
        when: "user wants to add a topic"
            topicFacade.addTopic(createTopic("Topic", "Description"))
        then: "he is not able to do this"
            thrown(UserRoleException.class)
    }

    def "guest can not add a new topic" () {
        given: "there is a guest"
            def guest = createGuest(userRepository)
            loggedUserGetter.getLoggedUserName() >> "Guest"
        when: "guest wants to add a new topic"
            topicFacade.addTopic(createTopic("Topic", "Description"))
        then: "he is not able to do this"
            thrown(UserRoleException.class)
    }

    def "topic can not be created with wrong content" () {
        given: "there is an admin"
            loggedUserGetter.getLoggedUserName() >> "Admin"
        when: "admin wants to add topic"
            topicFacade.addTopic(createTopic(name, description))
        then: "exception #excpected is thrown instead of creating a new topic"
            thrown(expected)
        where:
            name        |   description     |   expected
            longName    |   "Description"   |   InvalidTopicContentException.class
            "Name"      |   longDescription |   InvalidTopicContentException.class
            longName    |   longDescription |   InvalidTopicContentException.class
    }

    def "user wants to get a list of all topics" () {
        given: "there are three topics created by admin"
            loggedUserGetter.getLoggedUserName() >> "Admin"
            def fstTopic = topicFacade.addTopic(createTopic("First", "Description"))
            def sndTopic = topicFacade.addTopic(createTopic("Second", "Description"))
            def trdTopic = topicFacade.addTopic(createTopic("Third", "Description"))
        when: "user asks for list of topics"
            def topics = topicFacade.getTopics()
        then: "he gets list with three topics"
            topics.size() == 3
        and: "obtained topics are the same as those saved"
            [fstTopic.topicId, sndTopic.topicId, trdTopic.topicId].sort() == topics.stream()
                    .map({ topic -> topic.getTopicId()} )
                    .collect(Collectors.toList())
                    .sort()
    }

    def "user gets an empty list of topics if there was not any saved" () {
        given: "there is an user"
            loggedUserGetter.getLoggedUserName() >> DEF_LOGIN
        when: "he wants to get a list of topics"
            def topics = topicFacade.getTopics()
        then: "he gets an empty list of topics"
            topics.isEmpty()
    }

    private CreateTopicDto createTopic(String name, String description) {
        CreateTopicDto.builder()
            .name(name)
            .description(description)
            .build()
    }

    private static String longName = "Definitely too long!!!!!01234567899876543210"
    private static String longDescription = "Definitely too long description!!!!!01234567899876543210012345678998765432100123456789987654321001234567899876543210"
}
