package com.kamilmarnik.talkerr.post.domain;

import com.kamilmarnik.talkerr.post.dto.PostDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Builder(toBuilder = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String content;

  Date date;

  Long userId;

  static Post fromDto(PostDto dto) {
    return Post.builder()
        .id(dto.getId())
        .content(dto.getContent())
        .userId(dto.getUserId())
        .date(dto.getDate())
        .build();
  }

  public PostDto dto() {
    return PostDto.builder()
        .id(id)
        .content(content)
        .userId(userId)
        .date(date)
        .build();
  }
}
