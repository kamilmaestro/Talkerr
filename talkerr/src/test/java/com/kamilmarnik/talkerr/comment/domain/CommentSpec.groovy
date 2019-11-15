package com.kamilmarnik.talkerr.comment.domain


import com.kamilmarnik.talkerr.comment.dto.CreateCommentDto
import com.kamilmarnik.talkerr.logic.authentication.LoggedUserGetter
import com.kamilmarnik.talkerr.post.domain.PostFacade
import com.kamilmarnik.talkerr.post.dto.PostDto
import com.kamilmarnik.talkerr.user.domain.InMemoryUserRepository
import com.kamilmarnik.talkerr.user.domain.UserFacade
import com.kamilmarnik.talkerr.user.domain.UserFacadeCreator
import com.kamilmarnik.talkerr.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import static com.kamilmarnik.talkerr.user.domain.UserCreator.registerNewUser

class CommentSpec extends Specification {

    UserRepository userRepository = new InMemoryUserRepository()
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
    UserFacade userFacade = new UserFacadeCreator().createUserFacade(userRepository, passwordEncoder,loggedUserGetter)
    PostFacade postFacade = Mock(PostFacade.class)
    CommentRepository commentRepository = new InMemoryCommentRepository()
    CommentFacade commentFacade = new CommentFacadeCreator().createCommentFacade(commentRepository, userFacade, postFacade)

    def user = userFacade.registerUser(registerNewUser())

    def "user should be able add a comment to an existing post" () {
        given: "there is a post"
            loggedUserGetter.loggedUserName >> "DefLog"
            postFacade.addPost(_) >> PostDto.builder().postId(1).build()
        when: "user wants to add a comment to this post"
            def comment = commentFacade.addComment(CreateCommentDto.builder().postId(1).content("comment").build())
        then: "comment is created"
            comment.commentId == commentFacade.getComment(comment.commentId).commentId
    }
}
