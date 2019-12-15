package com.kamilmarnik.talkerr.topic.domain

import com.kamilmarnik.talkerr.topic.dto.CreateTopicDto

abstract class TopicCreator {

    static CreateTopicDto createNewTopic() {
        createNewTopic("Topic name", "Description name")
    }

    static CreateTopicDto createNewTopic(String name, String description) {
        CreateTopicDto.builder()
            .name(name)
            .description(description)
            .build()
    }
}
