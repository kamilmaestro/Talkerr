package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.exception.PostNotFoundException

import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class DeletePostSpec extends PostSpec {

    def "user should be able to delete a post if he is its creator" () {
        given: "there is an user"
            loggedUserGetter.loggedUserName >> "DefLog"
            def user = userFacade.registerUser(registerNewUser())
        and: "there is a post created by user"
            def post = postFacade.addPost(createNewPost())
        when: "user deletes the post"
            postFacade.deletePost(post.postId)
        and: "checks deleted post"
            postFacade.getPost(post.getPostId())
        then: "post is deleted"
            thrown PostNotFoundException.class
    }

    def "admin should be able to delete any post" () {
        given: "there is an user and admin"
            loggedUserGetter.loggedUserName >>> ["DefLog", "Admin"]
            def user = userFacade.registerUser(registerNewUser())
            def admin = createAdmin(userRepository)
        and: "there is a post created by user"
            def post = postFacade.addPost(createNewPost())
        when: "admin deletes the post created by other user"
            postFacade.deletePost(post.postId)
        and: "checks deleted post"
            postFacade.getPost(post.getPostId())
        then: "post is deleted"
            thrown PostNotFoundException.class
    }

    def "user should not be able to delete a post created by the other one" () {
        given: "there are two users"
            loggedUserGetter.loggedUserName >>> ["Creator", "Logged"]
            def postCreator = userFacade.registerUser(registerNewUser("Creator"))
            def loggedUser = userFacade.registerUser(registerNewUser("Logged"))
        and: "post is created by the other user"
            def post = postFacade.addPost(createNewPost())
        when: "logged in user wants to delete post added by the other one"
            postFacade.deletePost(post.postId)
        then: "post is not deleted"
            postFacade.getPost(post.postId).postId == post.postId
    }
}
