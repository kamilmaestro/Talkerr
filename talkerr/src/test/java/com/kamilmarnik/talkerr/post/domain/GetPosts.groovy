package com.kamilmarnik.talkerr.post.domain


import static com.kamilmarnik.talkerr.post.domain.PostCreator.createNewPost
import static com.kamilmarnik.talkerr.topic.domain.TopicCreator.createNewTopic
import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin

class GetPosts extends PostSpec {

    def "user wants to get a page of posts by topicId" () {
        given: "there is a topic created by admin"
            loggedUserGetter.loggedUserName >> "Admin"
            def admin = createAdmin(userRepository)
            topicFacade.addTopic(createNewTopic()) >> getTopic(admin.getUserId())
        and: "there are two posts in this topic"
            def post = postFacade.addPost(createNewPost(topicId))
            def sndPost = postFacade.addPost(createNewPost(topicId))
        when: "user asks for a page of posts"
            def topics = postFacade.getPostsByTopicId(PAGEABLE, topicId)
        then: "he gets 2 posts"
            topics.getContent().size() == 2
        and: "posts are connected with topic"
            topics.getContent().stream()
                    .filter({ p -> p.getTopicId() == topicId })
                    .count() == 2
    }
}
