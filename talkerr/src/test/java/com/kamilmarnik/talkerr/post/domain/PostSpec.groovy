package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.post.dto.PostDto
import spock.lang.Specification


class PostSpec extends Specification {

    PostFacade postFacade = new PostFacadeCreator().createPostFacade(new InMemoryPostRepository())

    def "user should be able to create a new post"() {
        given: "there is an user"
        when: "user creates a new post"
            def post = postFacade.addPost(PostDto.builder().build())
        then: "post is created"
            postFacade.getPost(post.postId)
    }
}
