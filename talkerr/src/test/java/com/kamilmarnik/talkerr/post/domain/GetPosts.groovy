package com.kamilmarnik.talkerr.post.domain


import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class GetPosts extends PostSpec {

    def setup() {
        loggedUserGetter.loggedUserName >> "DefLog"
        userFacade.registerUser(registerNewUser())
    }

    def "user wants to get a page of posts by topicId" () {
        given: "there are two posts in topic"
            def post = postFacade.addPost(createNewPost(fstTopicId))
            def sndPost = postFacade.addPost(createNewPost(fstTopicId))
        when: "user asks for a page of posts"
            def posts = postFacade.getPostsByTopicId(fstTopicId)
        then: "he gets 2 posts"
            posts.size() == 2
        and: "posts are connected with topic"
            posts.stream()
                    .filter({ p -> p.getTopicId() == fstTopicId })
                    .count() == 2
    }

    def "user should not get any post if he wants to get posts for topic which does not exist" () {
        given: "there are two posts in first topic"
            def post = postFacade.addPost(createNewPost(fstTopicId))
            def sndPost = postFacade.addPost(createNewPost(fstTopicId))
        when: "user asks for a page of posts created in second topic"
            def posts = postFacade.getPostsByTopicId(sndTopicId)
        then: "he gets an empty page"
            posts.size() == 0
    }
}
