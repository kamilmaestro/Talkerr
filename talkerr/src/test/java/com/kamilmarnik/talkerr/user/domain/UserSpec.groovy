package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserSpec extends Specification{
    UserRepository userRepository
    PasswordEncoder passwordEncoder
    LoggedUserGetter loggedUserGetter

    UserFacade createUserFacade() {
        setup()
        return new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder, loggedUserGetter)
    }

//    UserFacade createUserFacade(UserRepository userRepo) {
//        setup(userRepo)
//        return new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder, loggedUserGetter)
//    }

    private def setup() {
        loggedUserGetter = Mock(LoggedUserGetter.class)
        userRepository = new InMemoryUserRepository()
        passwordEncoder = new BCryptPasswordEncoder()
    }

//    private def setup(UserRepository userRepo) {
//        loggedUserGetter = Mock(LoggedUserGetter.class)
//        passwordEncoder = new BCryptPasswordEncoder()
//        userRepository = userRepo
//    }
}
