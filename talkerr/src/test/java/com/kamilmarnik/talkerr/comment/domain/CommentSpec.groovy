package com.kamilmarnik.talkerr.comment.domain

import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.post.domain.PostFacade
import com.kamilmarnik.talkerr.post.dto.PostDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

import java.time.LocalDateTime

class CommentSpec extends Specification {

    UserRepository userRepository = new InMemoryUserRepository()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    UserFacade userFacade = createUserFacade()
    PostFacade postFacade = Mock(PostFacade.class)
    CommentRepository commentRepository = new InMemoryCommentRepository()
    CommentFacade commentFacade = createCommentFacade()
    long fstPostId = 111L
    long sndPostId = 112L
    long topicId = 222L

    UserFacade createUserFacade() {
        new UserFacadeCreator().createUserFacade(userRepository, new BCryptPasswordEncoder(), loggedUserGetter)
    }

    CommentFacade createCommentFacade() {
        new CommentFacadeCreator().createCommentFacade(commentRepository, userFacade, postFacade)
    }

    PostDto getPost(long authorId, long postId) {
        PostDto.builder()
                .postId(postId)
                .content("Post")
                .createdOn(LocalDateTime.now())
                .authorId(authorId)
                .topicId(topicId)
                .build()
    }
}
