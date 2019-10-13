package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.user.dto.RegistrationRequest
import com.kamilmarnik.talkerr.user.dto.UserDto
import com.kamilmarnik.talkerr.user.dto.UserStatusDto

import java.time.LocalDateTime

abstract class UserCreator {
    static RegistrationRequest registerNewUser() {
        registerNewUser("DefLog", "DefPass123")
    }

    static RegistrationRequest registerNewUser(String login, String password) {
        RegistrationRequest.builder()
            .username(login)
            .password(password)
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
}
