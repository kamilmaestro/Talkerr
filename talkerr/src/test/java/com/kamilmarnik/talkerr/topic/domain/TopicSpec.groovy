package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.topic.dto.TopicDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.time.LocalDateTime

import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin

class TopicSpec extends Specification{

    UserRepository userRepository = new InMemoryUserRepository()
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    LoggedUserGetter loggedUserGetter = new LoggedUserGetter()
    TopicRepository topicRepository = new InMemoryTopicRepository()
    UserFacade userFacade = new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder,loggedUserGetter)
    TopicFacade topicFacade = new TopicFacadeCreator().createTopicFacade(topicRepository, userFacade)

    def "admin should be able to add a new topic" () {
        given: "there is an admin"
            def admin = createAdmin(userRepository)
        when: "admin creates a new topic"
            def topic = topicFacade.addTopic(createTopic(admin.userId))
        then: "the topic is created"
            topic.topicId == topicFacade.getTopic(topic.topicId).topicId
    }

    private TopicDto createTopic(long creatorId) {
        TopicDto.builder()
        .name("New topic")
        .createdOn(LocalDateTime.now())
        .creatorId(creatorId)
        .build()
    }
}
