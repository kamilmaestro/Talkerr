package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.PostDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.dto.UserDto
import com.kamilmarnik.talkerr.user.dto.UserStatusDto
import spock.lang.Specification

import java.time.LocalDateTime

class PostSpec extends Specification {

    UserFacade userFacade = new UserFacadeCreator().createUserFacade(new InMemoryUserRepository())
    PostFacade postFacade = new PostFacadeCreator().createPostFacade(new InMemoryPostRepository(), userFacade)

    long USER_ID = 1L
    long SND_USER_ID = 2L

    def "user should be able to create a new post"() {
        given: "there is an user"
            def user = userFacade.registerUser(registerNewUser(USER_ID, UserStatusDto.REGISTERED))
        when: "user creates a new post"
            def post = postFacade.addPost(createNewPost(user.userId, "FIRST POST"))
            def sndPost = postFacade.addPost(createNewPost(user.userId, "SECOND POST"))
        then: "post is created"
            def createdPost = postFacade.getPost(post.postId)
        and: "post was created correctly"
            createdPost.postId == post.postId
        and: "post was created by user"
            createdPost.userId == user.userId
    }

    private static PostDto createNewPost(long userId) {
        return createNewPost(userId, "DEFAULT CONTENT")
    }

    private static PostDto createNewPost(long userId, String content) {
        return createNewPost(userId, content, LocalDateTime.now().toDate())
    }

    private static PostDto createNewPost(long userId, String content, Date date) {
        return PostDto.builder()
            .content(content)
            .date(date)
            .userId(userId)
            .build()
    }

    private static UserDto registerNewUser(long userId, UserStatusDto status) {
        registerNewUser(userId, "DEFAULT_lOGIN", status, LocalDateTime.now().toDate())
    }

    private static UserDto registerNewUser(long userId, String login, UserStatusDto status, Date createdOn) {
        return UserDto.builder()
            .userId(userId)
            .login(login)
            .status(status)
            .createdOn(createdOn)
            .build()
    }
}
