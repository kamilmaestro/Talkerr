package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class PostSpec extends Specification{
    PasswordEncoder passwordEncoder
    LoggedUserGetter loggedUserGetter

    UserFacade createUserFacade(UserRepository userRepository) {
        setup()
        return new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder, loggedUserGetter)
    }

    private def setup() {
        loggedUserGetter = Mock(LoggedUserGetter.class)
        loggedUserGetter.loggedUserName >> "DefLog"
        passwordEncoder = new BCryptPasswordEncoder()
    }
}
