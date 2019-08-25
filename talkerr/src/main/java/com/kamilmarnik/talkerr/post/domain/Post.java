package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.PostDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "posts")
class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Column(name = "post_id")
  Long postId;

  @Column(name = "content")
  String content;

  @Column(name = "date")
  Date date;

  @Column(name = "user_id")
  Long userId;

  static Post fromDto(PostDto dto) {
    return Post.builder()
        .postId(dto.getPostId())
        .content(dto.getContent())
        .date(dto.getDate())
        .userId(dto.getUserId())
        .build();
  }

  public PostDto dto() {
    return PostDto.builder()
        .postId(postId)
        .content(content)
        .date(date)
        .userId(userId)
        .build();
  }
}
