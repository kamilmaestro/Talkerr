package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class UserSpec extends Specification{

    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    UserFacade userFacade = createUserFacade()

    UserFacade createUserFacade() {
        return new UserFacadeCreator().createUserFacade(new InMemoryUserRepository(), new BCryptPasswordEncoder(), loggedUserGetter)
    }
}
