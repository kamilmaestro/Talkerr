package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.exception.PostNotFoundException
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import spock.lang.Specification

import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class PostSpec extends Specification {

    UserRepository userRepository = new InMemoryUserRepository()
    UserFacade userFacade = new UserFacadeCreator().createUserFacade(userRepository)
    PostFacade postFacade = new PostFacadeCreator().createPostFacade(new InMemoryPostRepository(), userFacade)

    def ADMIN = createAdmin(userRepository)

    def "user should be able to create a new post"() {
        given: "there is an user"
            def user = userFacade.registerUser(registerNewUser())
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
            def user = userFacade.registerUser(registerNewUser())
        and: "there is a post created by user"
            def post = postFacade.addPost(createNewPost(user.userId, "POST"))
        when: "user deletes the post"
            postFacade.deletePost(post.postId, user.userId)
        and: "checks deleted post"
            postFacade.getPost(post.getPostId())
        then: "post is deleted"
            thrown PostNotFoundException.class
    }

    def "admin should be able to delete any post" () {
        given: "there is an user and admin"
            def user = userFacade.registerUser(registerNewUser())
        and: "there is a post created by user"
            def post = postFacade.addPost(createNewPost(user.userId, "POST"))
        when: "admin deletes the post created by other user"
            postFacade.deletePost(post.postId, ADMIN.userId)
        and: "checks deleted post"
            postFacade.getPost(post.getPostId())
        then: "post is deleted"
            thrown PostNotFoundException.class
    }
}
