package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.post.domain.PostFacade
import com.kamilmarnik.talkerr.topic.dto.TopicDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

import java.time.LocalDateTime

class TopicSpec extends Specification{

    UserRepository userRepository = new InMemoryUserRepository()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    TopicRepository topicRepository = new InMemoryTopicRepository()
    UserFacade userFacade = createUserFacade()
    PostFacade postFacade = Mock(PostFacade.class)
    TopicFacade topicFacade = createTopicFacade()
    long userId = 11L
    long fstTopicId = 111L
    long sndTopicId = 112L
    long fstPostId = 1111L
    long sndPostId = 1112L

    UserFacade createUserFacade() {
        new UserFacadeCreator().createUserFacade(userRepository, new BCryptPasswordEncoder(), loggedUserGetter)
    }

    TopicFacade createTopicFacade() {
        new TopicFacadeCreator().createTopicFacade(topicRepository, userFacade, postFacade)
    }

    TopicDto getTopic(long authorId) {
        TopicDto.builder()
                .topicId(fstTopicId)
                .name("Topic name")
                .description("Topic description")
                .createdOn(LocalDateTime.now())
                .authorId(authorId)
                .build()
    }
}
