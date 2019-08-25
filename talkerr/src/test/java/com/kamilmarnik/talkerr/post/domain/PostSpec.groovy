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

    PostFacade postFacade = new PostFacadeCreator().createPostFacade(new InMemoryPostRepository())
    UserFacade userFacade = new UserFacadeCreator().createUserFacade(new InMemoryUserRepository())

    long USER_ID = 1L
    long SND_USER_ID = 2L

    def "user should be able to create a new post"() {
        given: "there is an user"
            def user = userFacade.addUser(UserDto.builder().userId(USER_ID).status(UserStatusDto.LOGGED).build())
        when: "user creates a new post"
            def post = postFacade.addPost(createNewPost(user.userId))
            def sndPost = postFacade.addPost(PostDto.builder().build())
        then: "post is created"
            def createdPost = postFacade.getPost(post.postId)
        and: "post was created correctly"
            createdPost.postId == post.postId
        and: "post was created by user"
            createdPost.userId == user.userId
    }

    private PostDto createNewPost(long userId) {
        return createNewPost(userId, "DEFAULT CONTENT")
    }

    private PostDto createNewPost(long userId, String content) {
        return createNewPost(userId, content, LocalDateTime.now().toDate())
    }

    private PostDto createNewPost(long userId, String content, Date date) {
        return PostDto.builder()
            .content(content)
            .date(date)
            .userId(userId)
            .build()
    }
}
