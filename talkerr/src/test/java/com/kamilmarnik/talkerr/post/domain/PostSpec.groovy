package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.topic.domain.TopicFacade
import com.kamilmarnik.talkerr.topic.dto.TopicDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

import java.time.LocalDateTime

class PostSpec extends Specification{

    UserRepository userRepository = new InMemoryUserRepository()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    UserFacade userFacade = createUserFacade()
    TopicFacade topicFacade = Mock(TopicFacade.class)
    PostFacade postFacade = createPostFacade()
    long topicId = 111L

    UserFacade createUserFacade() {
        new UserFacadeCreator().createUserFacade(userRepository, new BCryptPasswordEncoder(), loggedUserGetter)
    }

    PostFacade createPostFacade() {
        new PostFacadeCreator().createPostFacade(new InMemoryPostRepository(), userFacade, topicFacade)
    }

    TopicDto getTopic(long authorId) {
        TopicDto.builder()
                .topicId(topicId)
                .name("Topic name")
                .description("Topic description")
                .createdOn(LocalDateTime.now())
                .authorId(authorId)
                .build()
    }
}
