package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.PostDto
import com.kamilmarnik.talkerr.post.exception.PostNotFoundException
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

    long ADMIN_ID = 54321L
    long USER_ID = 1L
    long SND_USER_ID = 2L
    def ADMIN = userFacade.registerUser(registerNewUser(USER_ID, UserStatusDto.ADMIN))

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

    def "user should be able to delete a post if he is its creator" () {
        given: "there is an user"
            def user = userFacade.registerUser(registerNewUser(USER_ID, UserStatusDto.REGISTERED))
        and: "there is a post created by user"
            def post = postFacade.addPost(createNewPost(user.userId, "POST"))
        when: "user deletes the post"
            postFacade.deletePost(post.postId, user.userId)
        and: "checks deleted post"
            postFacade.getPost(post.getPostId())
        then: "post is deleted"
            thrown PostNotFoundException.class
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
        registerNewUser(userId, "DefaultLogin", "DefaultPassword1", status, LocalDateTime.now().toDate())
    }

    private static UserDto registerNewUser(long userId, String login, String password, UserStatusDto status, Date createdOn) {
        return UserDto.builder()
            .userId(userId)
            .login(login)
            .password(password)
            .status(status)
            .createdOn(createdOn)
            .build()
    }
}
