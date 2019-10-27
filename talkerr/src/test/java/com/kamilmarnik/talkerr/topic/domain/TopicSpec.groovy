package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.topic.dto.CreateTopicDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import com.kamilmarnik.talkerr.user.dto.RegistrationRequest
import com.kamilmarnik.talkerr.user.exception.UserRoleException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin

class TopicSpec extends Specification{

    UserRepository userRepository = new InMemoryUserRepository()
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    TopicRepository topicRepository = new InMemoryTopicRepository()
    UserFacade userFacade = new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder,loggedUserGetter)
    TopicFacade topicFacade = new TopicFacadeCreator().createTopicFacade(topicRepository, userFacade)

    def "admin should be able to add a new topic" () {
        given: "there is an admin"
            def admin = createAdmin(userRepository)
            loggedUserGetter.getLoggedUserName() >> "Admin"
        when: "admin creates a new topic"
            def topic = topicFacade.addTopic(CreateTopicDto.builder().name("First topic").build())
        then: "the topic is created"
            topic.topicId == topicFacade.getTopic(topic.topicId).topicId
    }

    def "registered user without a permission should not be able to add a new topic" () {
        given: "there is an user"
            def user = userFacade.registerUser(RegistrationRequest.builder().username("DefLog").password("DefPass1").build())
            loggedUserGetter.getLoggedUserName() >> "DefLog"
        when: "user wants to add a topic"
            topicFacade.addTopic(CreateTopicDto.builder().name("Topic").build())
        then: "he is not able to do this"
            thrown(UserRoleException.class)
    }

}
