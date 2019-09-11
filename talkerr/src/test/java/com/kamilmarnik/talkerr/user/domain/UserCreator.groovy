package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.user.dto.LoggedUserDto
import com.kamilmarnik.talkerr.user.dto.UserDto
import com.kamilmarnik.talkerr.user.dto.UserStatusDto

import java.time.LocalDateTime

abstract class UserCreator {
    static LoggedUserDto registerNewUser() {
        registerNewUser("DefLog", "DefPass123")
    }

    static LoggedUserDto registerNewUser(String login, String password) {
        LoggedUserDto.builder()
            .login(login)
            .password(password)
            .build()
    }

    static UserDto createUserDto(UserStatusDto status) {
        createUserDto("DefLog", "DefPass123", status, LocalDateTime.now())
    }

    static UserDto createUserDto(String login, String password, UserStatusDto status, LocalDateTime createdOn) {
        UserDto.builder()
            .login(login)
            .password(password)
            .status(status)
            .registeredOn(createdOn)
            .build()
    }

    static UserDto createAdmin(UserRepository userRepository) {
        def admin = createUserDto(UserStatusDto.ADMIN)
        userRepository.save(User.fromDto(admin)).dto()
    }
}
