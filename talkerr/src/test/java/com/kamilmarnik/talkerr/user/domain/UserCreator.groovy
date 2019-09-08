package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.user.dto.LoggedUser
import com.kamilmarnik.talkerr.user.dto.UserDto
import com.kamilmarnik.talkerr.user.dto.UserStatusDto

import java.time.LocalDateTime

abstract class UserCreator {
    static LoggedUser registerNewUser() {
        registerNewUser("DefLog", "DefPass123")
    }

    static LoggedUser registerNewUser(String login, String password) {
        LoggedUser.builder()
            .login(login)
            .password(password)
            .build()
    }

    static UserDto createUserDto(UserStatusDto status) {
        createUserDto("DefLog", "DefPass123", status, LocalDateTime.now().toDate())
    }

    static UserDto createUserDto(String login, String password, UserStatusDto status, Date createdOn) {
        UserDto.builder()
            .login(login)
            .password(password)
            .status(status)
            .createdOn(createdOn)
            .build()
    }

    static UserDto createAdmin(UserRepository userRepository) {
        def admin = createUserDto(UserStatusDto.ADMIN)
        userRepository.save(User.fromDto(admin)).dto()
    }
}
