package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class PostSpec extends Specification{

    UserRepository userRepository = new InMemoryUserRepository()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    UserFacade userFacade = createUserFacade()
    PostFacade postFacade = createPostFacade()

    UserFacade createUserFacade() {
        new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder, loggedUserGetter)
    }

    PostFacade createPostFacade() {
        new PostFacadeCreator().createPostFacade(new InMemoryPostRepository(), userFacade)
    }
}
