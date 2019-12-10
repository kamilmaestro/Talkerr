package com.kamilmarnik.talkerr.topic.domain

import java.util.stream.Collectors

import static com.kamilmarnik.talkerr.topic.domain.TopicCreator.createNewTopic
import static com.kamilmarnik.talkerr.user.domain.UserCreator.*

class GetTopicsSpec extends TopicSpec {

    def setup() {
        def admin = createAdmin(userRepository)
        def user = userFacade.registerUser(registerNewUser())
    }

    def "user wants to get a list of all topics" () {
        given: "there are three topics created by admin"
            loggedUserGetter.getLoggedUserName() >> "Admin"
            def fstTopic = topicFacade.addTopic(createNewTopic("First", "Description"))
            def sndTopic = topicFacade.addTopic(createNewTopic("Second", "Description"))
            def trdTopic = topicFacade.addTopic(createNewTopic("Third", "Description"))
        when: "user asks for list of topics"
            def topics = topicFacade.getTopics()
        then: "he gets list with three topics"
            topics.size() == 3
        and: "obtained topics are the same as those saved"
            [fstTopic.topicId, sndTopic.topicId, trdTopic.topicId].sort() == topics.stream()
                    .map({ topic -> topic.getTopicId()} )
                    .collect(Collectors.toList())
                    .sort()
    }

    def "user gets an empty list of topics if there was not any saved" () {
        given: "there is an user"
            loggedUserGetter.getLoggedUserName() >> DEF_LOGIN
        when: "he wants to get a list of topics"
            def topics = topicFacade.getTopics()
        then: "he gets an empty list of topics"
            topics.isEmpty()
    }
}
