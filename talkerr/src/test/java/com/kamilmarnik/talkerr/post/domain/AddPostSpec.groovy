package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.PostDto
import com.kamilmarnik.talkerr.post.exception.InvalidPostContentException
import com.kamilmarnik.talkerr.user.exception.UserRoleException
import org.apache.commons.text.RandomStringGenerator

import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.user.domain.UserCreator.*

class AddPostSpec extends PostSpec {

    def setup() {
        userFacade.registerUser(registerNewUser())
    }
    private static tooLongContent = new RandomStringGenerator.Builder().build().generate(PostDto.MAX_CONTENT_LENGTH + 1)

    def "registered user should be able to add a new post" () {
        when: "user wants to add a new post"
            loggedUserGetter.loggedUserName >> DEF_LOGIN
            def post = postFacade.addPost(createNewPost("First post", fstTopicId))
        then: "post is created"
            def savedPost = postFacade.getPost(post.postId)
            savedPost.postId == post.postId
        and: "content is proper"
            savedPost.content == post.content
        and: "logged user is an author"
            savedPost.authorLogin == post.authorLogin
    }

    def "guest can not add a new post" () {
        given: "there is a guest"
            def guest = createGuest(userRepository)
            loggedUserGetter.getLoggedUserName() >> "Guest"
        when: "guest wants to add a new post"
            postFacade.addPost(createNewPost("Post", fstTopicId))
        then: "he is not able to do this"
            thrown(UserRoleException.class)
    }

    def "post can not be added due to invalid data" () {
        when: "user wants to add a post"
            loggedUserGetter.loggedUserName >> DEF_LOGIN
            postFacade.addPost(createNewPost(content, fstTopicId))
        then: "post is not added due to wrong content"
            thrown(expected)
        where:
            content         |   expected
            ""              |   InvalidPostContentException.class
            "   "           |   InvalidPostContentException.class
            tooLongContent  |   InvalidPostContentException.class
            null            |   InvalidPostContentException.class
    }
}
