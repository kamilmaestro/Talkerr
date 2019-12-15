package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.topic.exception.TopicNotFoundException

import static com.kamilmarnik.talkerr.user.domain.UserCreator.createAdmin
import static com.kamilmarnik.talkerr.topic.domain.TopicCreator.createNewTopic

class DeleteTopicSpec extends TopicSpec {

    def setup() {
        def admin = createAdmin(userRepository)
        loggedUserGetter.loggedUserName >> "Admin"
    }

    def "admin should be able to delete topic" () {
        given: "there is a topic"
            def topic = topicFacade.addTopic(createNewTopic())
        when: "admin deletes topic"
            topicFacade.deleteTopic(topic.topicId)
        and: "he checks if topic was deleted"
            topicFacade.getTopic(topic.topicId)
        then: "topic is deleted"
            thrown(TopicNotFoundException.class)
    }
}
