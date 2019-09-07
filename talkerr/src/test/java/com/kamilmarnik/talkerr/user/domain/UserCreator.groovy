package com.kamilmarnik.talkerr.user.domain

import com.kamilmarnik.talkerr.user.dto.UserDto
import com.kamilmarnik.talkerr.user.dto.UserStatusDto

import java.time.LocalDateTime

abstract class UserCreator {
    static UserDto registerNewUser(long userId, UserStatusDto status) {
        registerNewUser(userId, "DefaultLogin", "DefaultPassword1", status, LocalDateTime.now().toDate())
    }

    static UserDto registerNewUser(long userId, String login, String password, UserStatusDto status, Date createdOn) {
        return UserDto.builder()
                .userId(userId)
                .login(login)
                .password(password)
                .status(status)
                .createdOn(createdOn)
                .build()
    }
}
