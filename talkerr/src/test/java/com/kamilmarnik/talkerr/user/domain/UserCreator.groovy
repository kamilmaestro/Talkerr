package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.user.dto.RegistrationRequest
import com.kamilmarnik.talkerr.user.dto.UserDto
import com.kamilmarnik.talkerr.user.dto.UserStatusDto

import java.time.LocalDateTime

abstract class UserCreator {

    final static DEF_LOGIN = "DefLog"
    final static DEF_PASSWORD = "DefPass123"

    static RegistrationRequest registerNewUser() {
        registerNewUser(DEF_LOGIN)
    }

    static RegistrationRequest registerNewUser(String login) {
        RegistrationRequest.builder()
            .username(login)
            .password(DEF_PASSWORD)
            .build()
    }

    static UserDto createUserDto(String login, UserStatusDto status) {
        UserDto.builder()
            .login(login)
            .status(status)
            .registeredOn(LocalDateTime.now())
            .build()
    }

    static UserDto createAdmin(UserRepository userRepository) {
        def admin = createUserDto("Admin", UserStatusDto.ADMIN)
        userRepository.save(User.fromDto(admin)).dto()
    }

    static UserDto createGuest(UserRepository userRepository) {
        def guest = createUserDto("Guest", UserStatusDto.GUEST)
        userRepository.save(User.fromDto(guest)).dto()
    }
}
