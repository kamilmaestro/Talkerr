package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.user.dto.UserStatusDto
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class UserSpec extends Specification{

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    UserFacade userFacade = new UserFacadeCreator().createUserFacade(new InMemoryUserRepository(), passwordEncoder)

    def "user can be registered if there is no other user with such login" () {
        when: "user registers himself"
            def user = userFacade.registerUser(registerNewUser())
        then: "user is registered"
            def registeredUser = userFacade.getUser(user.userId)
        and: "login is correct"
            user.login == registeredUser.login
        and: "Id is correct"
            user.userId == registeredUser.userId
        and:
            registeredUser.status == UserStatusDto.REGISTERED
    }
}
