package com.kamilmarnik.talkerr.post.domain

import com.kamilmarnik.talkerr.comment.domain.CommentFacade
import com.kamilmarnik.talkerr.comment.dto.CommentDto
import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.post.dto.PostDto
import com.kamilmarnik.talkerr.topic.dto.TopicDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

import java.time.LocalDateTime

class PostSpec extends Specification{

    UserRepository userRepository = new InMemoryUserRepository()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    UserFacade userFacade = createUserFacade()
    CommentFacade commentFacade = Mock(CommentFacade.class)
    PostFacade postFacade = createPostFacade()
    long userId = 11L
    long fstTopicId = 111L
    long sndTopicId = 112L
    long fstPostId = 1111L
    long sndPostId = 1112L
    long fstCommentId = 11111L
    def PAGEABLE = PageRequest.of(0, 20)

    UserFacade createUserFacade() {
        new UserFacadeCreator().createUserFacade(userRepository, new BCryptPasswordEncoder(), loggedUserGetter)
    }

    PostFacade createPostFacade() {
        new PostFacadeCreator().createPostFacade(new InMemoryPostRepository(), userFacade, commentFacade)
    }

    TopicDto getTopic(long authorId) {
        TopicDto.builder()
                .topicId(fstTopicId)
                .name("Topic name")
                .description("Topic description")
                .createdOn(LocalDateTime.now())
                .authorId(authorId)
                .build()
    }

    CommentDto getComment(long authorId, long postId, long commentId) {
        CommentDto.builder()
            .commentId(commentId)
            .content("Def content")
            .createdOn(LocalDateTime.now())
            .postId(postId)
            .authorId(authorId)
            .build()

    }

    PostDto getPost(long authorId, long postId) {
        PostDto.builder()
                .postId(postId)
                .content("Post")
                .createdOn(LocalDateTime.now())
                .authorId(authorId)
                .topicId(fstTopicId)
                .build()
    }
}
